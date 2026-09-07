package com.hrms.billing.controller;

import com.hrms.billing.dto.*;
import com.hrms.billing.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
public class BillingController {
    private final BillingService billingService;
    public BillingController(BillingService billingService) { this.billingService = billingService; }
    @PostMapping("/create-order") public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) { return ResponseEntity.ok(billingService.createOrder(request)); }
    @PostMapping("/webhook") public WebhookResponse webhook(@RequestBody String payload, @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature, @RequestHeader(value = "X-Razorpay-Event-Id", required = false) String eventId) { return billingService.handleWebhook(payload, signature, eventId); }
}
