package com.hrms.billing.dto;

import java.util.UUID;

public record CreateOrderResponse(String orderId, String keyId, int amount, String currency, UUID subscriptionId) {
}
