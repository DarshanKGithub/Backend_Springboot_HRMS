package com.hrms.recruitment.dto;

import com.hrms.recruitment.entity.*;


import java.time.LocalDateTime;
import java.util.UUID;

public record JobPostingResponse(
        UUID id,
        String title,
        UUID departmentId,
        String location,
        String employmentType,
        String description,
        String requirements,
        String status,
        LocalDateTime createdAt
) {
    public static JobPostingResponse from(JobPosting jobPosting) {
        return new JobPostingResponse(
                jobPosting.getId(),
                jobPosting.getTitle(),
                jobPosting.getDepartmentId(),
                jobPosting.getLocation(),
                jobPosting.getEmploymentType(),
                jobPosting.getDescription(),
                jobPosting.getRequirements(),
                jobPosting.getStatus(),
                jobPosting.getCreatedAt()
        );
    }
}
