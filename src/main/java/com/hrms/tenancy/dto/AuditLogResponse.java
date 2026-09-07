package com.hrms.tenancy.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogResponse(UUID id, UUID actorId, String action, String objectType, UUID objectId, String data, LocalDateTime createdAt) {}
