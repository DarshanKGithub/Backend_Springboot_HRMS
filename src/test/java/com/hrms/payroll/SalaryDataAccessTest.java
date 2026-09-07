package com.hrms.payroll;

import com.hrms.payroll.entity.Salary;
import com.hrms.payroll.entity.SalaryComponent;
import com.hrms.payroll.repository.SalaryRepository;
import com.hrms.payroll.repository.SalaryComponentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SalaryDataAccessTest {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private SalaryComponentRepository salaryComponentRepository;

    @Test
    void shouldSaveAndLoadPayrollEntities() {
        Salary salary = new Salary();
        salary.setEmployeeId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        salary.setBaseSalary(new BigDecimal("120000.00"));
        salary.setGrade("L1");
        salary.setCurrency("INR");
        salary.setEffectiveFrom(LocalDate.of(2026, 1, 1));
        salary.setActive(true);

        Salary savedSalary = salaryRepository.save(salary);

        SalaryComponent component = new SalaryComponent();
        component.setName("House Rent Allowance");
        component.setComponentType("earning");
        component.setDescription("Monthly HRA");
        component.setActive(true);

        SalaryComponent savedComponent = salaryComponentRepository.save(component);

        assertThat(savedSalary.getId()).isNotNull();
        assertThat(salaryRepository.findById(savedSalary.getId())).isPresent();
        assertThat(salaryRepository.findById(savedSalary.getId()).get().getBaseSalary()).isEqualByComparingTo("120000.00");
        assertThat(savedComponent.getId()).isNotNull();
        assertThat(salaryComponentRepository.findById(savedComponent.getId())).isPresent();
    }
}
