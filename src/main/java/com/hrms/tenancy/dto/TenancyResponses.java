package com.hrms.tenancy.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TenancyResponses {
    private TenancyResponses() {}
    public record TenantResponse(UUID id, String name, String domain, boolean active) {}
    public record PlanResponse(UUID id, String name, int priceCents, String billingPeriod, String description, boolean active, List<String> featureKeys) {}
    public record PackageResponse(UUID id, String name, String description, int priceCents, List<String> featureKeys) {}
    public record SubscriptionResponse(UUID id, UUID tenantId, UUID planId, LocalDate startsAt, LocalDate endsAt, String status, int pricePaidCents) {}
    public record FeatureResponse(UUID id, UUID tenantId, String featureKey, boolean enabled) {}
    public record PaymentOrderResponse(UUID subscriptionId, String orderId, String keyId, int amountCents, String currency) {}
}
