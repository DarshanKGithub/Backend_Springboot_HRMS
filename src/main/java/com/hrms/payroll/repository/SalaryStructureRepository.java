package com.hrms.payroll.repository;

import com.hrms.payroll.entity.SalaryStructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, UUID> {
}
