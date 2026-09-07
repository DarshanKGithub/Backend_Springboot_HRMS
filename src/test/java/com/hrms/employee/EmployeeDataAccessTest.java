package com.hrms.employee;

import com.hrms.employee.entity.*;
import com.hrms.employee.repository.*;
import com.hrms.employee.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeDataAccessTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveAndLoadEmployee() {
        Employee employee = new Employee();
        employee.setFullName("Aisha Kumar");
        employee.setEmail("aisha@hrms.com");
        employee.setActive(true);

        Employee saved = employeeRepository.save(employee);

        assertThat(saved.getId()).isNotNull();
        assertThat(employeeRepository.findById(saved.getId())).isPresent();
        assertThat(employeeRepository.findById(saved.getId()).get().getEmail()).isEqualTo("aisha@hrms.com");
    }
}
