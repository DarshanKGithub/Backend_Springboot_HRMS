package com.hrms.tenancy.dto;

import java.util.List;

public record PackageRequest(String name, String description, int priceCents, List<String> featureKeys) {}
