package com.hrms.performance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PerformanceAppraisalRequest(UUID employeeId, UUID reviewerId, String reviewPeriod, String status, BigDecimal rating, String goalsAchieved, String areasForImprovement, String comments, LocalDate reviewDate, LocalDate nextReviewDate) {
}
