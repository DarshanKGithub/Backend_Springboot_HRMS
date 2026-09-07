package com.hrms.payroll.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salary_structures", uniqueConstraints = @UniqueConstraint(name = "uk_salary_structure_employee", columnNames = "employee_id"))
public class SalaryStructure {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "employee_id") private UUID employeeId;
    @Column(name = "base_salary", nullable = false, precision = 10, scale = 2) private BigDecimal baseSalary = BigDecimal.ZERO;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal hra = BigDecimal.ZERO;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal da = BigDecimal.ZERO;
    @Column(name = "special_allowance", nullable = false, precision = 10, scale = 2) private BigDecimal specialAllowance = BigDecimal.ZERO;
    @Column(name = "pf_percentage", nullable = false, precision = 5, scale = 2) private BigDecimal pfPercentage = new BigDecimal("12.00");
    @Column(name = "esi_percentage", nullable = false, precision = 5, scale = 2) private BigDecimal esiPercentage = new BigDecimal("0.75");
    @Column(name = "tax_bracket_percentage", nullable = false, precision = 5, scale = 2) private BigDecimal taxBracketPercentage = BigDecimal.ZERO;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }
    public BigDecimal getHra() { return hra; }
    public void setHra(BigDecimal hra) { this.hra = hra; }
    public BigDecimal getDa() { return da; }
    public void setDa(BigDecimal da) { this.da = da; }
    public BigDecimal getSpecialAllowance() { return specialAllowance; }
    public void setSpecialAllowance(BigDecimal specialAllowance) { this.specialAllowance = specialAllowance; }
    public BigDecimal getPfPercentage() { return pfPercentage; }
    public void setPfPercentage(BigDecimal pfPercentage) { this.pfPercentage = pfPercentage; }
    public BigDecimal getEsiPercentage() { return esiPercentage; }
    public void setEsiPercentage(BigDecimal esiPercentage) { this.esiPercentage = esiPercentage; }
    public BigDecimal getTaxBracketPercentage() { return taxBracketPercentage; }
    public void setTaxBracketPercentage(BigDecimal taxBracketPercentage) { this.taxBracketPercentage = taxBracketPercentage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
