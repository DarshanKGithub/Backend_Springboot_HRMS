package com.hrms.notifications.controller;

import com.hrms.notifications.dto.*;
import com.hrms.notifications.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/templates")
    public ResponseEntity<NotificationTemplateResponse> createTemplate(@RequestBody NotificationTemplateRequest request) {
        return ResponseEntity.status(201).body(notificationService.createTemplate(request));
    }

    @GetMapping("/templates")
    public List<NotificationTemplateResponse> listTemplates(@RequestParam(defaultValue = "100") int limit, @RequestParam(defaultValue = "0") int offset) {
        return notificationService.listTemplates(limit, offset);
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<NotificationTemplateResponse> getTemplate(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.getTemplate(id));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> send(@RequestBody NotificationRequest request) {
        return ResponseEntity.status(201).body(notificationService.send(request));
    }

    @GetMapping("/user/{userId}/notifications")
    public List<NotificationResponse> listForUser(@PathVariable UUID userId, @RequestParam(defaultValue = "false") boolean unreadOnly,
                                                   @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {
        return notificationService.listForUser(userId, unreadOnly, limit, offset);
    }

    @GetMapping("/user/{userId}/unread-count")
    public Map<String, Long> unreadCount(@PathVariable UUID userId) {
        return Map.of("unread_count", notificationService.unreadCount(userId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markRead(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.markRead(id));
    }
}
