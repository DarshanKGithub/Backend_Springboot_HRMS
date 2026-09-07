package com.hrms.performance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GoalResponse(UUID id, UUID employeeId, UUID appraisalId, String title, String description, String status, LocalDate startDate, LocalDate endDate, BigDecimal targetValue, BigDecimal achievedValue, BigDecimal achievementPercentage) {
}
