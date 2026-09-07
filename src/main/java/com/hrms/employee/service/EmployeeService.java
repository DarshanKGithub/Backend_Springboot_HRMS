package com.hrms.employee.service;

import com.hrms.employee.entity.*;
import com.hrms.employee.repository.*;
import com.hrms.employee.dto.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> listEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFullName(request.fullName());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setDepartmentId(request.departmentId());
        employee.setDesignationId(request.designationId());
        employee.setManagerId(request.managerId());
        employee.setActive(request.active());

        Employee saved = employeeRepository.save(employee);
        return EmployeeResponse.from(saved);
    }

    public EmployeeResponse getEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
        return EmployeeResponse.from(employee);
    }
}
