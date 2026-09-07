package com.hrms.attendance;

import com.hrms.attendance.entity.*;
import com.hrms.attendance.repository.*;
import com.hrms.attendance.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AttendanceDataAccessTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    void shouldSaveAndLoadAttendance() {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(java.util.UUID.fromString("11111111-1111-1111-1111-111111111111"));
        attendance.setAttendanceDate(LocalDate.of(2026, 9, 6));
        attendance.setCheckInTime(LocalDateTime.of(2026, 9, 6, 9, 0));
        attendance.setStatus("present");
        attendance.setLate(false);
        attendance.setLateMinutes(0);
        attendance.setHalfDay(false);
        attendance.setWorkFromHome(false);

        Attendance saved = attendanceRepository.save(attendance);

        assertThat(saved.getId()).isNotNull();
        assertThat(attendanceRepository.findById(saved.getId())).isPresent();
        assertThat(attendanceRepository.findById(saved.getId()).get().getStatus()).isEqualTo("present");
    }
}
