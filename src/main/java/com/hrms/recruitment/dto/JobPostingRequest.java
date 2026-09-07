package com.hrms.recruitment.dto;

import com.hrms.recruitment.entity.*;


import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record JobPostingRequest(
        @NotBlank(message = "Title is required")
        String title,
        UUID departmentId,
        String location,
        String employmentType,
        @NotBlank(message = "Description is required")
        String description,
        String requirements,
        String status
) {}
