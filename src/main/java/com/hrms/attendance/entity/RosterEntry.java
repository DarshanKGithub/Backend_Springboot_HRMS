package com.hrms.attendance.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "roster_entries")
public class RosterEntry {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "roster_id", nullable = false) private UUID rosterId;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(nullable = false) private LocalDate date;
    @Column(name = "shift_id") private UUID shiftId;
    @Column(name = "is_off_day", nullable = false) private boolean offDay;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getRosterId() { return rosterId; }
    public void setRosterId(UUID rosterId) { this.rosterId = rosterId; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public UUID getShiftId() { return shiftId; }
    public void setShiftId(UUID shiftId) { this.shiftId = shiftId; }
    public boolean isOffDay() { return offDay; }
    public void setOffDay(boolean offDay) { this.offDay = offDay; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
