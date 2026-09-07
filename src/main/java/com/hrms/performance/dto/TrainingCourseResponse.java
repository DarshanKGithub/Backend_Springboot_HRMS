package com.hrms.performance.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TrainingCourseResponse(UUID id, String title, String description, String instructor, BigDecimal durationHours) {
}
