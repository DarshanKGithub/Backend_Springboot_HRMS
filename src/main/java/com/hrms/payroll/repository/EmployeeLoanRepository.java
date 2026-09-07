package com.hrms.payroll.repository;

import com.hrms.payroll.entity.EmployeeLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmployeeLoanRepository extends JpaRepository<EmployeeLoan, UUID> {
}
