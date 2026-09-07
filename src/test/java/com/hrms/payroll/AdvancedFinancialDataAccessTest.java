package com.hrms.payroll;

import com.hrms.payroll.entity.EmployeeLoan;
import com.hrms.payroll.entity.Reimbursement;
import com.hrms.payroll.entity.SalaryStructure;
import com.hrms.payroll.repository.EmployeeLoanRepository;
import com.hrms.payroll.repository.ReimbursementRepository;
import com.hrms.payroll.repository.SalaryStructureRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AdvancedFinancialDataAccessTest {

    @Autowired private SalaryStructureRepository salaryStructureRepository;
    @Autowired private ReimbursementRepository reimbursementRepository;
    @Autowired private EmployeeLoanRepository employeeLoanRepository;

    @Test
    void shouldSaveAndLoadAdvancedFinancialEntities() {
        UUID employeeId = UUID.randomUUID();

        SalaryStructure structure = new SalaryStructure();
        structure.setEmployeeId(employeeId);
        structure.setBaseSalary(new BigDecimal("75000.00"));
        structure.setHra(new BigDecimal("30000.00"));
        structure.setDa(new BigDecimal("7500.00"));
        structure.setSpecialAllowance(new BigDecimal("10000.00"));
        SalaryStructure savedStructure = salaryStructureRepository.save(structure);

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setEmployeeId(employeeId);
        reimbursement.setExpenseType("Travel");
        reimbursement.setAmount(new BigDecimal("2450.50"));
        reimbursement.setDescription("Client visit");
        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);

        EmployeeLoan loan = new EmployeeLoan();
        loan.setEmployeeId(employeeId);
        loan.setLoanType("Advance Salary");
        loan.setAmount(new BigDecimal("50000.00"));
        loan.setInterestRate(new BigDecimal("0.00"));
        loan.setEmiAmount(new BigDecimal("10000.00"));
        loan.setRemainingBalance(new BigDecimal("50000.00"));
        EmployeeLoan savedLoan = employeeLoanRepository.save(loan);

        assertThat(salaryStructureRepository.findById(savedStructure.getId()))
                .get().extracting(SalaryStructure::getBaseSalary)
                .isEqualTo(new BigDecimal("75000.00"));
        assertThat(reimbursementRepository.findById(savedReimbursement.getId())).isPresent();
        assertThat(employeeLoanRepository.findById(savedLoan.getId()))
                .get().extracting(EmployeeLoan::getRemainingBalance)
                .isEqualTo(new BigDecimal("50000.00"));
    }
}
