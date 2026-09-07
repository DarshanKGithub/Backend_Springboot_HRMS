package com.hrms.leave;

import com.hrms.leave.entity.*;
import com.hrms.leave.repository.*;
import com.hrms.leave.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LeaveSupportDataAccessTest {

    @Autowired private LeaveTypeRepository leaveTypeRepository;
    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private TodoItemRepository todoItemRepository;
    @Autowired private HolidayRepository holidayRepository;

    @Test
    void shouldSaveAndLoadLeaveSupportEntities() {
        UUID employeeId = UUID.randomUUID();

        LeaveType leaveType = new LeaveType();
        leaveType.setName("Annual Leave");
        leaveType.setDefaultDaysPerYear(20);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);

        LeaveBalance balance = new LeaveBalance();
        balance.setEmployeeId(employeeId);
        balance.setLeaveTypeId(savedLeaveType.getId());
        balance.setYear(2026);
        balance.setGrantedDays(20);
        balance.setBalanceDays(18);
        LeaveBalance savedBalance = leaveBalanceRepository.save(balance);

        TodoItem todo = new TodoItem();
        todo.setEmployeeId(employeeId);
        todo.setTitle("Complete policy acknowledgement");
        todo.setDueDate(LocalDate.of(2026, 9, 15));
        TodoItem savedTodo = todoItemRepository.save(todo);

        Holiday holiday = new Holiday();
        holiday.setName("Founders Day");
        holiday.setHolidayDate(LocalDate.of(2026, 9, 20));
        Holiday savedHoliday = holidayRepository.save(holiday);

        assertThat(leaveBalanceRepository.findById(savedBalance.getId()))
                .get().extracting(LeaveBalance::getLeaveTypeId)
                .isEqualTo(savedLeaveType.getId());
        assertThat(todoItemRepository.findById(savedTodo.getId())).isPresent();
        assertThat(holidayRepository.findById(savedHoliday.getId()))
                .get().extracting(Holiday::getHolidayDate)
                .isEqualTo(LocalDate.of(2026, 9, 20));
    }
}
