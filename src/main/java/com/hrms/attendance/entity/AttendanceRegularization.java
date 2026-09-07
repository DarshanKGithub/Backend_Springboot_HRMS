package com.hrms.attendance.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance_regularizations")
public class AttendanceRegularization {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(name = "attendance_id") private UUID attendanceId;
    @Column(nullable = false) private LocalDate date;
    @Column(nullable = false, length = 1000) private String reason;
    @Column(name = "requested_check_in") private LocalDateTime requestedCheckIn;
    @Column(name = "requested_check_out") private LocalDateTime requestedCheckOut;
    @Column(nullable = false, length = 30) private String status = "Pending";
    @Column(name = "approver_id") private UUID approverId;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public UUID getAttendanceId() { return attendanceId; }
    public void setAttendanceId(UUID attendanceId) { this.attendanceId = attendanceId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getRequestedCheckIn() { return requestedCheckIn; }
    public void setRequestedCheckIn(LocalDateTime requestedCheckIn) { this.requestedCheckIn = requestedCheckIn; }
    public LocalDateTime getRequestedCheckOut() { return requestedCheckOut; }
    public void setRequestedCheckOut(LocalDateTime requestedCheckOut) { this.requestedCheckOut = requestedCheckOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public UUID getApproverId() { return approverId; }
    public void setApproverId(UUID approverId) { this.approverId = approverId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
