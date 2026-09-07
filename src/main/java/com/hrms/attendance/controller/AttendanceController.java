package com.hrms.attendance.controller;

import com.hrms.attendance.service.*;
import com.hrms.attendance.dto.*;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<AttendanceResponse> listAttendances(@RequestParam(required = false) UUID employeeId,
                                                    @RequestParam(required = false) java.time.LocalDate startDate,
                                                    @RequestParam(required = false) java.time.LocalDate endDate) {
        return attendanceService.listAttendances(employeeId, startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.createAttendance(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getAttendance(@PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.getAttendance(id));
    }

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(@Valid @RequestBody CheckOutRequest request) {
        return ResponseEntity.ok(attendanceService.checkOut(request));
    }

    @PostMapping("/start-break")
    public ResponseEntity<AttendanceResponse> startBreak(@Valid @RequestBody BreakRequest request) {
        return ResponseEntity.ok(attendanceService.startBreak(request));
    }

    @PostMapping("/end-break")
    public ResponseEntity<AttendanceResponse> endBreak(@Valid @RequestBody BreakRequest request) {
        return ResponseEntity.ok(attendanceService.endBreak(request));
    }

    @GetMapping("/summary/{employeeId}")
    public AttendanceSummaryResponse summary(@PathVariable UUID employeeId, @RequestParam java.time.LocalDate startDate,
                                             @RequestParam java.time.LocalDate endDate) {
        if (startDate.isAfter(endDate)) throw new IllegalArgumentException("startDate must be before endDate");
        return attendanceService.summary(employeeId, startDate, endDate);
    }
}
