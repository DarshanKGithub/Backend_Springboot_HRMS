package com.hrms.notifications.service;

import com.hrms.notifications.dto.*;
import com.hrms.notifications.entity.*;
import com.hrms.notifications.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationTemplateRepository templateRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationTemplateRepository templateRepository, NotificationRepository notificationRepository) {
        this.templateRepository = templateRepository;
        this.notificationRepository = notificationRepository;
    }

    public NotificationTemplateResponse createTemplate(NotificationTemplateRequest request) {
        NotificationTemplate template = new NotificationTemplate();
        apply(template, request);
        return toResponse(templateRepository.save(template));
    }

    public List<NotificationTemplateResponse> listTemplates(int limit, int offset) {
        return templateRepository.findAll().stream().skip(offset).limit(limit).map(this::toResponse).toList();
    }

    public NotificationTemplateResponse getTemplate(UUID id) {
        return templateRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Template not found: " + id));
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientId(request.recipientId());
        notification.setTemplateId(request.templateId());
        notification.setEventType(request.eventType());
        notification.setChannel(request.channel());
        notification.setSubject(request.subject());
        notification.setMessage(request.message());
        notification.setData(request.data());
        notification.setStatus("Sent");
        notification.setSentAt(LocalDateTime.now());
        return toResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> listForUser(UUID userId, boolean unreadOnly, int limit, int offset) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId).stream()
                .filter(item -> !unreadOnly || item.getReadAt() == null)
                .skip(offset).limit(limit).map(this::toResponse).toList();
    }

    public long unreadCount(UUID userId) {
        return notificationRepository.countByRecipientIdAndReadAtIsNull(userId);
    }

    public NotificationResponse markRead(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
        notification.setStatus("Read");
        notification.setReadAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return toResponse(notificationRepository.save(notification));
    }

    private void apply(NotificationTemplate template, NotificationTemplateRequest request) {
        template.setName(request.name());
        template.setDescription(request.description());
        template.setEventType(request.eventType());
        template.setChannel(request.channel());
        template.setSubject(request.subject());
        template.setBody(request.body());
        template.setActive(request.active());
    }

    private NotificationTemplateResponse toResponse(NotificationTemplate item) {
        return new NotificationTemplateResponse(item.getId(), item.getName(), item.getDescription(), item.getEventType(), item.getChannel(), item.getSubject(), item.getBody(), item.isActive(), item.getCreatedAt(), item.getUpdatedAt());
    }

    private NotificationResponse toResponse(Notification item) {
        return new NotificationResponse(item.getId(), item.getRecipientId(), item.getTemplateId(), item.getEventType(), item.getChannel(), item.getSubject(), item.getMessage(), item.getData(), item.getStatus(), item.getReadAt(), item.getSentAt(), item.getCreatedAt());
    }
}
