package com.hrms.workflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkflowTemplateResponse(UUID id, String name, String triggerEvent, String stepsJson, boolean active, LocalDateTime createdAt) {
}
