package com.hrms.payroll.repository;

import com.hrms.payroll.entity.SalaryComponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, UUID> {
}
