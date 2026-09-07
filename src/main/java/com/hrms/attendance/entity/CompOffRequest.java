package com.hrms.attendance.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comp_off_requests")
public class CompOffRequest {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(name = "worked_date", nullable = false) private LocalDate workedDate;
    @Column(length = 1000) private String reason;
    @Column(name = "leave_balance_id") private UUID leaveBalanceId;
    @Column(nullable = false, length = 30) private String status = "Pending";
    @Column(name = "approver_id") private UUID approverId;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public LocalDate getWorkedDate() { return workedDate; }
    public void setWorkedDate(LocalDate workedDate) { this.workedDate = workedDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public UUID getLeaveBalanceId() { return leaveBalanceId; }
    public void setLeaveBalanceId(UUID leaveBalanceId) { this.leaveBalanceId = leaveBalanceId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public UUID getApproverId() { return approverId; }
    public void setApproverId(UUID approverId) { this.approverId = approverId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
