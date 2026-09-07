package com.hrms.recruitment.dto;

import com.hrms.recruitment.entity.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CandidateRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        String phone,
        String resumeUrl,
        String parsedSkills,
        String source
) {}
