package com.hrms.workflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FormTemplateResponse(UUID id, String name, String description, String schemaJson, boolean active, LocalDateTime createdAt) {
}
