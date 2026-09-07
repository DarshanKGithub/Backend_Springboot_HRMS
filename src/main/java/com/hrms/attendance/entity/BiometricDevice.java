package com.hrms.attendance.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "biometric_devices")
public class BiometricDevice {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "device_id", nullable = false, unique = true, length = 100) private String deviceId;
    @Column(nullable = false, length = 120) private String name;
    @Column(length = 255) private String location;
    @Column(name = "ip_address", length = 50) private String ipAddress;
    @Column(nullable = false, length = 30) private String status = "Active";
    @Column(name = "last_sync_time") private LocalDateTime lastSyncTime;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getLastSyncTime() { return lastSyncTime; }
    public void setLastSyncTime(LocalDateTime lastSyncTime) { this.lastSyncTime = lastSyncTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
