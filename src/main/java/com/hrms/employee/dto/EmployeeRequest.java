package com.hrms.employee.dto;

import com.hrms.employee.entity.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EmployeeRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 120, message = "Full name must be at most 120 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String phone,
        UUID departmentId,
        UUID designationId,
        UUID managerId,
        boolean active
) {}
