package com.hrms.performance.dto;

import java.math.BigDecimal;

public record TrainingCourseRequest(String title, String description, String instructor, BigDecimal durationHours) {
}
