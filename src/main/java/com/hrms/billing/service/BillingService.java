package com.hrms.billing.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.billing.dto.*;
import com.hrms.billing.entity.PaymentWebhookEvent;
import com.hrms.billing.repository.PaymentWebhookEventRepository;
import com.hrms.tenancy.entity.Plan;
import com.hrms.tenancy.entity.Subscription;
import com.hrms.tenancy.repository.PlanRepository;
import com.hrms.tenancy.repository.SubscriptionRepository;
import com.hrms.tenancy.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
public class BillingService {
    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentWebhookEventRepository eventRepository;
    private final ObjectMapper objectMapper;
    private final String keyId;
    private final String keySecret;
    private final String webhookSecret;

    public BillingService(TenantRepository tenantRepository, PlanRepository planRepository, SubscriptionRepository subscriptionRepository,
                          PaymentWebhookEventRepository eventRepository, ObjectMapper objectMapper,
                          @Value("${app.razorpay.key-id:}") String keyId,
                          @Value("${app.razorpay.key-secret:}") String keySecret,
                          @Value("${app.razorpay.webhook-secret:}") String webhookSecret) {
        this.tenantRepository = tenantRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
        this.keyId = keyId;
        this.keySecret = keySecret;
        this.webhookSecret = webhookSecret;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        requireCredentials();
        if (!tenantRepository.existsById(request.tenantId())) throw new IllegalArgumentException("Tenant not found: " + request.tenantId());
        Plan plan = planRepository.findById(request.planId()).orElseThrow(() -> new IllegalArgumentException("Plan not found: " + request.planId()));
        Map<String, Object> order = RestClient.builder().baseUrl("https://api.razorpay.com/v1")
                .defaultHeaders(headers -> headers.setBasicAuth(keyId, keySecret)).build()
                .post().uri("/orders").body(Map.of("amount", plan.getPriceCents(), "currency", "INR", "receipt", "sub_" + request.tenantId() + "_" + request.planId(), "payment_capture", 1)).retrieve().body(Map.class);
        String orderId = order == null ? null : String.valueOf(order.get("id"));
        if (orderId == null || "null".equals(orderId)) throw new IllegalStateException("Razorpay did not return an order id");
        Subscription subscription = new Subscription();
        subscription.setTenantId(request.tenantId()); subscription.setPlanId(request.planId()); subscription.setExternalOrderId(orderId);
        subscription.setPricePaidCents(plan.getPriceCents()); subscription.setStartsAt(LocalDate.now()); subscription.setStatus("pending");
        Subscription saved = subscriptionRepository.save(subscription);
        return new CreateOrderResponse(orderId, keyId, plan.getPriceCents(), "INR", saved.getId());
    }

    @Transactional
    public WebhookResponse handleWebhook(String payload, String signature, String eventHeader) {
        verifyWebhookSignature(payload, signature);
        try {
            JsonNode event = objectMapper.readTree(payload);
            String eventType = event.path("event").asText("unknown");
            String eventId = eventHeader == null || eventHeader.isBlank() ? sha256(payload) : eventHeader;
            if (eventRepository.existsByEventId(eventId)) return new WebhookResponse("ok", true);
            String orderId = extractOrderId(event);
            PaymentWebhookEvent processed = new PaymentWebhookEvent();
            processed.setEventId(eventId); processed.setEventType(eventType); processed.setOrderId(orderId);
            eventRepository.save(processed);
            transitionSubscription(eventType, orderId);
            return new WebhookResponse("success", false);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid webhook payload", exception);
        }
    }

    private void transitionSubscription(String eventType, String orderId) {
        if (orderId == null || orderId.isBlank()) return;
        Subscription subscription = subscriptionRepository.findByExternalOrderId(orderId).orElse(null);
        if (subscription == null) return;
        if (eventType.equals("payment.captured") || eventType.equals("order.paid")) {
            subscription.setStatus("active");
            Plan plan = planRepository.findById(subscription.getPlanId()).orElse(null);
            if (plan != null) subscription.setEndsAt(subscription.getStartsAt().plusDays(durationDays(plan.getBillingPeriod())));
        } else if (eventType.equals("payment.failed") || eventType.equals("order.payment_failed")) {
            subscription.setStatus("payment_failed");
        }
        subscriptionRepository.save(subscription);
    }

    private int durationDays(String period) { return switch (period == null ? "monthly" : period.toLowerCase()) { case "trial" -> 14; case "yearly" -> 365; case "3-month" -> 90; case "6-month" -> 180; case "9-month" -> 270; default -> 30; }; }
    private String extractOrderId(JsonNode event) { JsonNode payment = event.path("payload").path("payment").path("entity").path("order_id"); if (!payment.isMissingNode() && !payment.asText().isBlank()) return payment.asText(); JsonNode order = event.path("payload").path("order").path("entity").path("id"); return order.isMissingNode() ? null : order.asText(); }
    private void requireCredentials() { if (keyId.isBlank() || keySecret.isBlank()) throw new IllegalStateException("Razorpay credentials are not configured"); }
    private void verifyWebhookSignature(String payload, String signature) { if (webhookSecret.isBlank()) throw new IllegalStateException("Razorpay webhook secret is not configured"); if (signature == null || signature.isBlank()) throw new IllegalArgumentException("Missing Razorpay signature"); try { Mac mac = Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256")); byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)); StringBuilder expected = new StringBuilder(); for (byte value : digest) expected.append(String.format("%02x", value)); if (!MessageDigest.isEqual(expected.toString().getBytes(StandardCharsets.UTF_8), signature.getBytes(StandardCharsets.UTF_8))) throw new IllegalArgumentException("Invalid webhook signature"); } catch (IllegalArgumentException exception) { throw exception; } catch (Exception exception) { throw new IllegalStateException("Unable to verify webhook signature", exception); } }
    private String sha256(String value) { try { byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)); StringBuilder result = new StringBuilder(); for (byte item : digest) result.append(String.format("%02x", item)); return result.toString(); } catch (Exception exception) { throw new IllegalStateException(exception); } }
}
