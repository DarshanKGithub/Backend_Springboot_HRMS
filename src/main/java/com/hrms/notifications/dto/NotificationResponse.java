package com.hrms.notifications.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(UUID id, UUID recipientId, UUID templateId, String eventType, String channel, String subject, String message, String data, String status, LocalDateTime readAt, LocalDateTime sentAt, LocalDateTime createdAt) {
}
