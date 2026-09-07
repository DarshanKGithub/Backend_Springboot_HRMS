package com.hrms.attendance.dto;

import com.hrms.attendance.entity.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceRequest(
        @NotNull(message = "Employee id is required")
        UUID employeeId,

        @NotNull(message = "Attendance date is required")
        LocalDate attendanceDate,

        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,

        @NotBlank(message = "Status is required")
        String status,

        boolean late,
        int lateMinutes,
        boolean halfDay,
        boolean workFromHome,
        String notes
) {}
