package com.hrms.attendance;

import com.hrms.attendance.entity.*;
import com.hrms.attendance.repository.*;
import com.hrms.attendance.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BiometricRosterDataAccessTest {

    @Autowired private ShiftRepository shiftRepository;
    @Autowired private EmployeeShiftAssignmentRepository assignmentRepository;
    @Autowired private AttendanceRuleRepository attendanceRuleRepository;
    @Autowired private BiometricDeviceRepository deviceRepository;
    @Autowired private BiometricLogRepository logRepository;
    @Autowired private RosterRepository rosterRepository;
    @Autowired private RosterEntryRepository rosterEntryRepository;

    @Test
    void shouldSaveAndLoadBiometricRosterEntities() {
        UUID employeeId = UUID.randomUUID();

        Shift shift = new Shift();
        shift.setName("Morning");
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(18, 0));
        shift.setGracePeriodMinutes(5);
        Shift savedShift = shiftRepository.save(shift);

        EmployeeShiftAssignment assignment = new EmployeeShiftAssignment();
        assignment.setEmployeeId(employeeId);
        assignment.setShiftId(savedShift.getId());
        assignment.setStartDate(LocalDate.of(2026, 9, 1));
        EmployeeShiftAssignment savedAssignment = assignmentRepository.save(assignment);

        AttendanceRule rule = new AttendanceRule();
        rule.setName("Late entry");
        rule.setRuleType("late_entry");
        rule.setThresholdMinutes(30);
        AttendanceRule savedRule = attendanceRuleRepository.save(rule);

        BiometricDevice device = new BiometricDevice();
        device.setDeviceId("DEVICE-001");
        device.setName("Front gate scanner");
        device.setLocation("Main entrance");
        BiometricDevice savedDevice = deviceRepository.save(device);

        BiometricLog log = new BiometricLog();
        log.setDeviceId(savedDevice.getId());
        log.setEmployeeId(employeeId);
        log.setTimestamp(LocalDateTime.of(2026, 9, 6, 9, 2));
        log.setLogType("In");
        BiometricLog savedLog = logRepository.save(log);

        Roster roster = new Roster();
        roster.setName("September roster");
        roster.setStartDate(LocalDate.of(2026, 9, 1));
        roster.setEndDate(LocalDate.of(2026, 9, 30));
        Roster savedRoster = rosterRepository.save(roster);

        RosterEntry entry = new RosterEntry();
        entry.setRosterId(savedRoster.getId());
        entry.setEmployeeId(employeeId);
        entry.setDate(LocalDate.of(2026, 9, 6));
        entry.setShiftId(savedShift.getId());
        RosterEntry savedEntry = rosterEntryRepository.save(entry);

        assertThat(shiftRepository.findById(savedShift.getId())).isPresent();
        assertThat(assignmentRepository.findById(savedAssignment.getId())).isPresent();
        assertThat(attendanceRuleRepository.findById(savedRule.getId())).isPresent();
        assertThat(logRepository.findById(savedLog.getId())).isPresent();
        assertThat(rosterEntryRepository.findById(savedEntry.getId())).isPresent();
    }
}
