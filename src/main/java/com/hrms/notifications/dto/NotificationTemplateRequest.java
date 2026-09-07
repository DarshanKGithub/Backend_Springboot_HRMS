package com.hrms.notifications.dto;

public record NotificationTemplateRequest(String name, String description, String eventType, String channel, String subject, String body, boolean active) {
}
