package com.hrms.attendance.dto;

import com.hrms.attendance.entity.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceResponse(
        UUID id,
        UUID employeeId,
        LocalDate attendanceDate,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String status,
        boolean late,
        int lateMinutes,
        boolean halfDay,
        boolean workFromHome,
        String notes
) {
    public static AttendanceResponse from(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getEmployeeId(),
                attendance.getAttendanceDate(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getStatus(),
                attendance.isLate(),
                attendance.getLateMinutes(),
                attendance.isHalfDay(),
                attendance.isWorkFromHome(),
                attendance.getNotes()
        );
    }
}
