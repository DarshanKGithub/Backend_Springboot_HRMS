package com.hrms.tenancy.dto;

public record TenantRequest(String name, String domain, Boolean active) {}
