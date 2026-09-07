package com.hrms.payroll.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payslips")
public class Payslip {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(nullable = false) private int month;
    @Column(nullable = false) private int year;
    @Column(name = "base_salary", nullable = false, precision = 12, scale = 2) private BigDecimal baseSalary;
    @Column(name = "gross_salary", nullable = false, precision = 12, scale = 2) private BigDecimal grossSalary;
    @Column(name = "total_deductions", nullable = false, precision = 12, scale = 2) private BigDecimal totalDeductions = BigDecimal.ZERO;
    @Column(name = "total_tax", nullable = false, precision = 12, scale = 2) private BigDecimal totalTax = BigDecimal.ZERO;
    @Column(name = "net_salary", nullable = false, precision = 12, scale = 2) private BigDecimal netSalary;
    @Column(name = "days_worked", nullable = false) private int daysWorked;
    @Column(name = "days_absent", nullable = false) private int daysAbsent;
    @Column(nullable = false, length = 30) private String status = "draft";
    @Column(name = "processed_by") private UUID processedBy;
    @Column(name = "processed_at") private LocalDateTime processedAt;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public void setGrossSalary(BigDecimal grossSalary) { this.grossSalary = grossSalary; }
    public BigDecimal getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(BigDecimal totalDeductions) { this.totalDeductions = totalDeductions; }
    public BigDecimal getTotalTax() { return totalTax; }
    public void setTotalTax(BigDecimal totalTax) { this.totalTax = totalTax; }
    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }
    public int getDaysWorked() { return daysWorked; }
    public void setDaysWorked(int daysWorked) { this.daysWorked = daysWorked; }
    public int getDaysAbsent() { return daysAbsent; }
    public void setDaysAbsent(int daysAbsent) { this.daysAbsent = daysAbsent; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public UUID getProcessedBy() { return processedBy; }
    public void setProcessedBy(UUID processedBy) { this.processedBy = processedBy; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
