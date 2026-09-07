package com.hrms.payroll.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_loans")
public class EmployeeLoan {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id") private UUID employeeId;
    @Column(name = "loan_type", nullable = false, length = 50) private String loanType;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal amount;
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2) private BigDecimal interestRate = BigDecimal.ZERO;
    @Column(name = "emi_amount", nullable = false, precision = 10, scale = 2) private BigDecimal emiAmount;
    @Column(name = "remaining_balance", nullable = false, precision = 10, scale = 2) private BigDecimal remainingBalance;
    @Column(nullable = false, length = 30) private String status = "Active";
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    public BigDecimal getEmiAmount() { return emiAmount; }
    public void setEmiAmount(BigDecimal emiAmount) { this.emiAmount = emiAmount; }
    public BigDecimal getRemainingBalance() { return remainingBalance; }
    public void setRemainingBalance(BigDecimal remainingBalance) { this.remainingBalance = remainingBalance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
