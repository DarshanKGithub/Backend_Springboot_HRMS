package com.hrms.performance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record KpiResponse(UUID id, UUID employeeId, UUID goalId, String title, String description, String status, BigDecimal targetValue, BigDecimal achievedValue, BigDecimal weightage, LocalDate startDate, LocalDate endDate) {
}
