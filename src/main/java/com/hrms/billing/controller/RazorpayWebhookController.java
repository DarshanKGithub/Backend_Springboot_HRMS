package com.hrms.billing.controller;

import com.hrms.billing.dto.WebhookResponse;
import com.hrms.billing.service.BillingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class RazorpayWebhookController {
    private final BillingService billingService;
    public RazorpayWebhookController(BillingService billingService) { this.billingService = billingService; }
    @PostMapping("/razorpay") public WebhookResponse razorpay(@RequestBody String payload, @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature, @RequestHeader(value = "X-Razorpay-Event-Id", required = false) String eventId) { return billingService.handleWebhook(payload, signature, eventId); }
}
