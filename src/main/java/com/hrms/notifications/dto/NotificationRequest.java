package com.hrms.notifications.dto;

import java.util.UUID;

public record NotificationRequest(UUID recipientId, UUID templateId, String eventType, String channel, String subject, String message, String data) {
}
