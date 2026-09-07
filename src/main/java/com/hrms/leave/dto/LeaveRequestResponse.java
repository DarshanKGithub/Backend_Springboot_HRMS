package com.hrms.leave.dto;

import com.hrms.leave.entity.*;


import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestResponse(
        UUID id,
        UUID employeeId,
        UUID leaveTypeId,
        LocalDate startDate,
        LocalDate endDate,
        int daysRequested,
        String reason,
        String status
) {
    public static LeaveRequestResponse from(LeaveRequest leaveRequest) {
        return new LeaveRequestResponse(
                leaveRequest.getId(),
                leaveRequest.getEmployeeId(),
                leaveRequest.getLeaveTypeId(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getDaysRequested(),
                leaveRequest.getReason(),
                leaveRequest.getStatus()
        );
    }
}
