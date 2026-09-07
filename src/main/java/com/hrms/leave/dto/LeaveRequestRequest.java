package com.hrms.leave.dto;

import com.hrms.leave.entity.*;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestRequest(
        @NotNull(message = "Employee id is required")
        UUID employeeId,

        @NotNull(message = "Leave type id is required")
        UUID leaveTypeId,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        int daysRequested,
        String reason,
        String status
) {}
