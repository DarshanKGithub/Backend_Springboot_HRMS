package com.hrms.notifications.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationTemplateResponse(UUID id, String name, String description, String eventType, String channel, String subject, String body, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
