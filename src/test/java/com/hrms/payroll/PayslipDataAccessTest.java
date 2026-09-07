package com.hrms.payroll;

import com.hrms.payroll.entity.EmployeeSalaryComponent;
import com.hrms.payroll.entity.Payslip;
import com.hrms.payroll.entity.PayslipComponent;
import com.hrms.payroll.entity.SalaryComponent;
import com.hrms.payroll.entity.SalaryHistory;
import com.hrms.payroll.repository.EmployeeSalaryComponentRepository;
import com.hrms.payroll.repository.PayslipComponentRepository;
import com.hrms.payroll.repository.PayslipRepository;
import com.hrms.payroll.repository.SalaryComponentRepository;
import com.hrms.payroll.repository.SalaryHistoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PayslipDataAccessTest {

    @Autowired private SalaryComponentRepository salaryComponentRepository;
    @Autowired private EmployeeSalaryComponentRepository employeeSalaryComponentRepository;
    @Autowired private PayslipRepository payslipRepository;
    @Autowired private PayslipComponentRepository payslipComponentRepository;
    @Autowired private SalaryHistoryRepository salaryHistoryRepository;

    @Test
    void shouldSaveAndLoadPayslipEntities() {
        UUID employeeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        SalaryComponent salaryComponent = new SalaryComponent();
        salaryComponent.setName("House Rent Allowance");
        salaryComponent.setComponentType("earning");
        SalaryComponent savedSalaryComponent = salaryComponentRepository.save(salaryComponent);

        EmployeeSalaryComponent employeeComponent = new EmployeeSalaryComponent();
        employeeComponent.setEmployeeId(employeeId);
        employeeComponent.setSalaryComponentId(savedSalaryComponent.getId());
        employeeComponent.setAmount(new BigDecimal("25000.00"));
        EmployeeSalaryComponent savedEmployeeComponent = employeeSalaryComponentRepository.save(employeeComponent);

        Payslip payslip = new Payslip();
        payslip.setEmployeeId(employeeId);
        payslip.setMonth(9);
        payslip.setYear(2026);
        payslip.setBaseSalary(new BigDecimal("75000.00"));
        payslip.setGrossSalary(new BigDecimal("100000.00"));
        payslip.setNetSalary(new BigDecimal("85000.00"));
        payslip.setDaysWorked(22);
        Payslip savedPayslip = payslipRepository.save(payslip);

        PayslipComponent payslipComponent = new PayslipComponent();
        payslipComponent.setPayslipId(savedPayslip.getId());
        payslipComponent.setSalaryComponentId(savedSalaryComponent.getId());
        payslipComponent.setComponentName("House Rent Allowance");
        payslipComponent.setComponentType("earning");
        payslipComponent.setAmount(new BigDecimal("25000.00"));
        PayslipComponent savedPayslipComponent = payslipComponentRepository.save(payslipComponent);

        SalaryHistory history = new SalaryHistory();
        history.setEmployeeId(employeeId);
        history.setSalaryId(UUID.randomUUID());
        history.setBaseSalary(new BigDecimal("75000.00"));
        history.setEffectiveFrom(LocalDate.of(2026, 9, 1));
        history.setReasonForChange("promotion");
        history.setModifiedBy(userId);
        SalaryHistory savedHistory = salaryHistoryRepository.save(history);

        assertThat(employeeSalaryComponentRepository.findById(savedEmployeeComponent.getId())).isPresent();
        assertThat(payslipRepository.findById(savedPayslip.getId()))
                .get().extracting(Payslip::getNetSalary)
                .isEqualTo(new BigDecimal("85000.00"));
        assertThat(payslipComponentRepository.findById(savedPayslipComponent.getId())).isPresent();
        assertThat(salaryHistoryRepository.findById(savedHistory.getId()))
                .get().extracting(SalaryHistory::getEffectiveFrom)
                .isEqualTo(LocalDate.of(2026, 9, 1));
    }
}
