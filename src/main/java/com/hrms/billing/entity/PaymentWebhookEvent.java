package com.hrms.billing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_webhook_events")
public class PaymentWebhookEvent {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "event_id", nullable = false, unique = true, length = 200) private String eventId;
    @Column(name = "event_type", nullable = false, length = 100) private String eventType;
    @Column(name = "order_id", length = 100) private String orderId;
    @Column(nullable = false, length = 30) private String status = "processed";
    @Column(name = "processed_at", nullable = false) private LocalDateTime processedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
