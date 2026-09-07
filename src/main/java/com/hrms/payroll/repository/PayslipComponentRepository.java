package com.hrms.payroll.repository;

import com.hrms.payroll.entity.PayslipComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PayslipComponentRepository extends JpaRepository<PayslipComponent, UUID> {
}
