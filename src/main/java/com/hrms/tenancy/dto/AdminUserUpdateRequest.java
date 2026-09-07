package com.hrms.tenancy.dto;

import java.util.UUID;

public record AdminUserUpdateRequest(String role, UUID tenantId) {}
