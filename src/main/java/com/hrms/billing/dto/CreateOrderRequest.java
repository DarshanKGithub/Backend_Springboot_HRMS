package com.hrms.billing.dto;

import java.util.UUID;

public record CreateOrderRequest(UUID tenantId, UUID planId) {
}
