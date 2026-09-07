package com.hrms.employee.repository;

import com.hrms.employee.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
