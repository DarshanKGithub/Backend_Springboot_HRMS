package com.hrms.tenancy.dto;

public record PaymentVerificationRequest(String paymentId, String orderId, String signature) {}
