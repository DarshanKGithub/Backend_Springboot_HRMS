package com.hrms.lifecycle.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "exit_cases")
public class ExitCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "exit_type", nullable = false, length = 100)
    private String exitType;

    @Column(length = 2000)
    private String reason;

    @Column(name = "notice_period_end")
    private LocalDate noticePeriodEnd;

    @Column(name = "last_working_day")
    private LocalDate lastWorkingDay;

    @Column(name = "settlement_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal settlementAmount = BigDecimal.ZERO;

    @Column(nullable = false, length = 30)
    private String status = "Requested";

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getNoticePeriodEnd() {
        return noticePeriodEnd;
    }

    public void setNoticePeriodEnd(LocalDate noticePeriodEnd) {
        this.noticePeriodEnd = noticePeriodEnd;
    }

    public LocalDate getLastWorkingDay() {
        return lastWorkingDay;
    }

    public void setLastWorkingDay(LocalDate lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
