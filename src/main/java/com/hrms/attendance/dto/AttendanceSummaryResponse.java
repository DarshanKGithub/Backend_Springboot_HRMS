package com.hrms.attendance.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AttendanceSummaryResponse(UUID employeeId, LocalDate startDate, LocalDate endDate, long presentDays, long absentDays, long halfDays, long workFromHomeDays) {
}
