package com.hrms.org.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "actor_id") private UUID actorId;
    @Column(nullable = false, length = 60) private String action;
    @Column(name = "object_type", nullable = false, length = 60) private String objectType;
    @Column(name = "object_id") private UUID objectId;
    @Column(length = 2000) private String data;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getActorId() { return actorId; }
    public void setActorId(UUID actorId) { this.actorId = actorId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }
    public UUID getObjectId() { return objectId; }
    public void setObjectId(UUID objectId) { this.objectId = objectId; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
