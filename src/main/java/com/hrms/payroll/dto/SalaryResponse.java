package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SalaryResponse(UUID id, UUID employeeId, BigDecimal baseSalary, String grade, String currency,
                             LocalDate effectiveFrom, LocalDate effectiveTo, boolean active) {
}
