package com.hrms.leave.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(name = "leave_type_id", nullable = false) private UUID leaveTypeId;
    @Column(nullable = false) private int year;
    @Column(name = "granted_days", nullable = false) private int grantedDays;
    @Column(name = "balance_days", nullable = false) private int balanceDays;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public UUID getLeaveTypeId() { return leaveTypeId; }
    public void setLeaveTypeId(UUID leaveTypeId) { this.leaveTypeId = leaveTypeId; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getGrantedDays() { return grantedDays; }
    public void setGrantedDays(int grantedDays) { this.grantedDays = grantedDays; }
    public int getBalanceDays() { return balanceDays; }
    public void setBalanceDays(int balanceDays) { this.balanceDays = balanceDays; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
