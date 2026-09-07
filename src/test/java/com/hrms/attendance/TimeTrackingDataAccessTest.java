package com.hrms.attendance;

import com.hrms.attendance.entity.*;
import com.hrms.attendance.repository.*;
import com.hrms.attendance.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TimeTrackingDataAccessTest {

    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private AttendanceBreakRepository attendanceBreakRepository;
    @Autowired private TimesheetRepository timesheetRepository;
    @Autowired private OvertimeRequestRepository overtimeRequestRepository;
    @Autowired private AttendanceRegularizationRepository regularizationRepository;
    @Autowired private CompOffRequestRepository compOffRequestRepository;

    @Test
    void shouldSaveAndLoadTimeTrackingEntities() {
        UUID employeeId = UUID.randomUUID();
        LocalDate workDate = LocalDate.of(2026, 9, 6);

        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setAttendanceDate(workDate);
        attendance.setStatus("present");
        Attendance savedAttendance = attendanceRepository.save(attendance);

        AttendanceBreak attendanceBreak = new AttendanceBreak();
        attendanceBreak.setAttendanceId(savedAttendance.getId());
        attendanceBreak.setStartTime(LocalDateTime.of(2026, 9, 6, 13, 0));
        AttendanceBreak savedBreak = attendanceBreakRepository.save(attendanceBreak);

        Timesheet timesheet = new Timesheet();
        timesheet.setEmployeeId(employeeId);
        timesheet.setDate(workDate);
        timesheet.setTaskDescription("Implement attendance integration");
        timesheet.setHoursWorked(new BigDecimal("7.50"));
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        OvertimeRequest overtime = new OvertimeRequest();
        overtime.setEmployeeId(employeeId);
        overtime.setAttendanceId(savedAttendance.getId());
        overtime.setDate(workDate);
        overtime.setHours(new BigDecimal("2.00"));
        overtime.setReason("Release support");
        OvertimeRequest savedOvertime = overtimeRequestRepository.save(overtime);

        AttendanceRegularization regularization = new AttendanceRegularization();
        regularization.setEmployeeId(employeeId);
        regularization.setAttendanceId(savedAttendance.getId());
        regularization.setDate(workDate);
        regularization.setReason("Missed check-in");
        regularization.setRequestedCheckIn(LocalDateTime.of(2026, 9, 6, 9, 5));
        AttendanceRegularization savedRegularization = regularizationRepository.save(regularization);

        CompOffRequest compOff = new CompOffRequest();
        compOff.setEmployeeId(employeeId);
        compOff.setWorkedDate(workDate);
        compOff.setReason("Worked on weekend");
        CompOffRequest savedCompOff = compOffRequestRepository.save(compOff);

        assertThat(attendanceBreakRepository.findById(savedBreak.getId())).isPresent();
        assertThat(timesheetRepository.findById(savedTimesheet.getId()))
                .get().extracting(Timesheet::getHoursWorked)
                .isEqualTo(new BigDecimal("7.50"));
        assertThat(overtimeRequestRepository.findById(savedOvertime.getId())).isPresent();
        assertThat(regularizationRepository.findById(savedRegularization.getId())).isPresent();
        assertThat(compOffRequestRepository.findById(savedCompOff.getId())).isPresent();
    }
}
