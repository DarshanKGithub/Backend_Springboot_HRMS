package com.hrms.org.dto;

import com.hrms.org.entity.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentRequest(
        @NotBlank(message = "Department name is required")
        @Size(min = 1, max = 120, message = "Department name must be between 1 and 120 characters")
        String name
) {}
