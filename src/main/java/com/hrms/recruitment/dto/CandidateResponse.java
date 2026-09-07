package com.hrms.recruitment.dto;

import com.hrms.recruitment.entity.*;


import java.time.LocalDateTime;
import java.util.UUID;

public record CandidateResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String resumeUrl,
        String parsedSkills,
        String source,
        LocalDateTime createdAt
) {
    public static CandidateResponse from(Candidate candidate) {
        return new CandidateResponse(
                candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getResumeUrl(),
                candidate.getParsedSkills(),
                candidate.getSource(),
                candidate.getCreatedAt()
        );
    }
}
