package com.hrms.performance.repository;

import com.hrms.performance.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, UUID> {
}
