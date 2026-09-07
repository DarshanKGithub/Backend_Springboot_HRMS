package com.hrms.employee.dto;

import com.hrms.employee.entity.*;


import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        UUID departmentId,
        UUID designationId,
        UUID managerId,
        boolean active
) {
    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartmentId(),
                employee.getDesignationId(),
                employee.getManagerId(),
                employee.isActive()
        );
    }
}
