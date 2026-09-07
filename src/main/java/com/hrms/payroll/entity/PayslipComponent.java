package com.hrms.payroll.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payslip_components")
public class PayslipComponent {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "payslip_id", nullable = false) private UUID payslipId;
    @Column(name = "salary_component_id", nullable = false) private UUID salaryComponentId;
    @Column(name = "component_name", nullable = false, length = 120) private String componentName;
    @Column(name = "component_type", nullable = false, length = 50) private String componentType;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPayslipId() { return payslipId; }
    public void setPayslipId(UUID payslipId) { this.payslipId = payslipId; }
    public UUID getSalaryComponentId() { return salaryComponentId; }
    public void setSalaryComponentId(UUID salaryComponentId) { this.salaryComponentId = salaryComponentId; }
    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }
    public String getComponentType() { return componentType; }
    public void setComponentType(String componentType) { this.componentType = componentType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
