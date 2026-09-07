package com.hrms.tenancy.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SubscriptionRequest(UUID planId, LocalDate startsAt, Integer durationMonths, String status) {}
