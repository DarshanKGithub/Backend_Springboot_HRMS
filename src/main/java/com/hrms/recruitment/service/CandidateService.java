package com.hrms.recruitment.service;

import com.hrms.recruitment.entity.*;
import com.hrms.recruitment.repository.*;
import com.hrms.recruitment.dto.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateResponse> listCandidates() {
        return candidateRepository.findAll().stream()
                .map(CandidateResponse::from)
                .toList();
    }

    public CandidateResponse createCandidate(CandidateRequest request) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(request.firstName());
        candidate.setLastName(request.lastName());
        candidate.setEmail(request.email());
        candidate.setPhone(request.phone());
        candidate.setResumeUrl(request.resumeUrl());
        candidate.setParsedSkills(request.parsedSkills());
        candidate.setSource(request.source());

        Candidate saved = candidateRepository.save(candidate);
        return CandidateResponse.from(saved);
    }

    public CandidateResponse getCandidate(UUID id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found: " + id));
        return CandidateResponse.from(candidate);
    }
}
