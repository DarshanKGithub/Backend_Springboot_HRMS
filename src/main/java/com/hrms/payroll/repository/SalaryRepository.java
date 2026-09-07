package com.hrms.payroll.repository;

import com.hrms.payroll.entity.Salary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, UUID> {
	Optional<Salary> findByEmployeeId(UUID employeeId);
}
