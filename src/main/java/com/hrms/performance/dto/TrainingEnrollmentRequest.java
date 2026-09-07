package com.hrms.performance.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TrainingEnrollmentRequest(UUID employeeId, UUID courseId, String status, LocalDate completionDate) {
}
