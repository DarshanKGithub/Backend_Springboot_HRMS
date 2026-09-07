package com.hrms.leave.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "holidays")
public class Holiday {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "tenant_id") private UUID tenantId;
    @Column(nullable = false, length = 120) private String name;
    @Column(name = "holiday_date", nullable = false) private LocalDate holidayDate;
    @Column(name = "is_public", nullable = false) private boolean publicHoliday = true;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getHolidayDate() { return holidayDate; }
    public void setHolidayDate(LocalDate holidayDate) { this.holidayDate = holidayDate; }
    public boolean isPublicHoliday() { return publicHoliday; }
    public void setPublicHoliday(boolean publicHoliday) { this.publicHoliday = publicHoliday; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
