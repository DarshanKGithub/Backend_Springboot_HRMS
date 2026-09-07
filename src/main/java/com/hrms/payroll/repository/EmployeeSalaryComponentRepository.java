package com.hrms.payroll.repository;

import com.hrms.payroll.entity.EmployeeSalaryComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmployeeSalaryComponentRepository extends JpaRepository<EmployeeSalaryComponent, UUID> {
}
