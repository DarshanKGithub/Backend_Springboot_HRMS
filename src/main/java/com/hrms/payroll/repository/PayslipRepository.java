package com.hrms.payroll.repository;

import com.hrms.payroll.entity.Payslip;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PayslipRepository extends JpaRepository<Payslip, UUID> {
}
