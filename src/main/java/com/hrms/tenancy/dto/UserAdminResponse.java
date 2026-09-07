package com.hrms.tenancy.dto;

import java.util.UUID;

public record UserAdminResponse(UUID id, String fullName, String email, String role, UUID tenantId, boolean active) {}
