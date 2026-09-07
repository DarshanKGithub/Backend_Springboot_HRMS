package com.hrms.payroll.repository;

import com.hrms.payroll.entity.Reimbursement;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, UUID> {
}
