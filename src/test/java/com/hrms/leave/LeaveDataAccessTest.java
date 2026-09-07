package com.hrms.leave;

import com.hrms.leave.entity.*;
import com.hrms.leave.repository.*;
import com.hrms.leave.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LeaveDataAccessTest {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Test
    void shouldSaveAndLoadLeaveRequest() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(java.util.UUID.fromString("22222222-2222-2222-2222-222222222222"));
        leaveRequest.setLeaveTypeId(java.util.UUID.fromString("33333333-3333-3333-3333-333333333333"));
        leaveRequest.setStartDate(LocalDate.of(2026, 9, 10));
        leaveRequest.setEndDate(LocalDate.of(2026, 9, 12));
        leaveRequest.setDaysRequested(3);
        leaveRequest.setStatus("pending");

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        assertThat(saved.getId()).isNotNull();
        assertThat(leaveRequestRepository.findById(saved.getId())).isPresent();
        assertThat(leaveRequestRepository.findById(saved.getId()).get().getStatus()).isEqualTo("pending");
    }
}
