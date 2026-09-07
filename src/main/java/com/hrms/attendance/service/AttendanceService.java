package com.hrms.attendance.service;

import com.hrms.attendance.entity.*;
import com.hrms.attendance.repository.*;
import com.hrms.attendance.dto.*;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceBreakRepository attendanceBreakRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceBreakRepository attendanceBreakRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceBreakRepository = attendanceBreakRepository;
    }

    public List<AttendanceResponse> listAttendances() {
        return attendanceRepository.findAll().stream()
                .map(AttendanceResponse::from)
                .toList();
    }

    public AttendanceResponse createAttendance(AttendanceRequest request) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(request.employeeId());
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setCheckInTime(request.checkInTime());
        attendance.setCheckOutTime(request.checkOutTime());
        attendance.setStatus(request.status());
        attendance.setLate(request.late());
        attendance.setLateMinutes(request.lateMinutes());
        attendance.setHalfDay(request.halfDay());
        attendance.setWorkFromHome(request.workFromHome());
        attendance.setNotes(request.notes());

        Attendance saved = attendanceRepository.save(attendance);
        return AttendanceResponse.from(saved);
    }

    public AttendanceResponse getAttendance(UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found: " + id));
        return AttendanceResponse.from(attendance);
    }

    public AttendanceResponse checkIn(CheckInRequest request) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findAll().stream()
                .filter(item -> request.employeeId().equals(item.getEmployeeId()) && today.equals(item.getAttendanceDate()))
                .findFirst().orElseGet(() -> {
                    Attendance item = new Attendance();
                    item.setEmployeeId(request.employeeId());
                    item.setAttendanceDate(today);
                    return item;
                });
        if (attendance.getCheckInTime() != null) {
            throw new IllegalStateException("Employee is already checked in");
        }
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus("present");
        return AttendanceResponse.from(attendanceRepository.save(attendance));
    }

    public AttendanceResponse checkOut(CheckOutRequest request) {
        Attendance attendance = todayAttendance(request.employeeId());
        if (attendance.getCheckInTime() == null) {
            throw new IllegalStateException("Employee has not checked in");
        }
        attendance.setCheckOutTime(LocalDateTime.now());
        return AttendanceResponse.from(attendanceRepository.save(attendance));
    }

    public AttendanceResponse startBreak(BreakRequest request) {
        Attendance attendance = todayAttendance(request.employeeId());
        AttendanceBreak attendanceBreak = new AttendanceBreak();
        attendanceBreak.setAttendanceId(attendance.getId());
        attendanceBreak.setBreakType(request.breakType() == null ? "lunch" : request.breakType());
        attendanceBreak.setStartTime(LocalDateTime.now());
        attendanceBreakRepository.save(attendanceBreak);
        return AttendanceResponse.from(attendance);
    }

    public AttendanceResponse endBreak(BreakRequest request) {
        Attendance attendance = todayAttendance(request.employeeId());
        AttendanceBreak openBreak = attendanceBreakRepository.findAll().stream()
                .filter(item -> attendance.getId().equals(item.getAttendanceId()) && item.getEndTime() == null)
                .reduce((first, second) -> second).orElseThrow(() -> new IllegalStateException("No open break found"));
        openBreak.setEndTime(LocalDateTime.now());
        attendanceBreakRepository.save(openBreak);
        return AttendanceResponse.from(attendance);
    }

    public List<AttendanceResponse> listAttendances(UUID employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findAll().stream()
                .filter(item -> employeeId == null || employeeId.equals(item.getEmployeeId()))
                .filter(item -> startDate == null || !item.getAttendanceDate().isBefore(startDate))
                .filter(item -> endDate == null || !item.getAttendanceDate().isAfter(endDate))
                .map(AttendanceResponse::from).toList();
    }

    public AttendanceSummaryResponse summary(UUID employeeId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> records = attendanceRepository.findAll().stream()
                .filter(item -> employeeId.equals(item.getEmployeeId()))
                .filter(item -> !item.getAttendanceDate().isBefore(startDate) && !item.getAttendanceDate().isAfter(endDate)).toList();
        return new AttendanceSummaryResponse(employeeId, startDate, endDate,
                records.stream().filter(item -> "present".equalsIgnoreCase(item.getStatus())).count(),
                records.stream().filter(item -> "absent".equalsIgnoreCase(item.getStatus())).count(),
                records.stream().filter(Attendance::isHalfDay).count(),
                records.stream().filter(Attendance::isWorkFromHome).count());
    }

    private Attendance todayAttendance(UUID employeeId) {
        return attendanceRepository.findAll().stream()
                .filter(item -> employeeId.equals(item.getEmployeeId()) && LocalDate.now().equals(item.getAttendanceDate()))
                .findFirst().orElseThrow(() -> new IllegalStateException("No attendance record for today"));
    }
}
