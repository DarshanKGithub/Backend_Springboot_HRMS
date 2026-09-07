package com.hrms.workflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkflowInstanceResponse(UUID id, UUID templateId, UUID targetId, int currentStep, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
