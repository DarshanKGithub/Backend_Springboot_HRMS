package com.hrms.tenancy.dto;

import java.util.List;

public record PlanRequest(String name, int priceCents, String billingPeriod, String description, Boolean active, List<String> featureKeys) {}
