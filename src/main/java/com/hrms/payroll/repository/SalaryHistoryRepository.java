package com.hrms.payroll.repository;

import com.hrms.payroll.entity.SalaryHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, UUID> {
}
