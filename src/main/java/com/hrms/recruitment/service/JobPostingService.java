package com.hrms.recruitment.service;

import com.hrms.recruitment.entity.*;
import com.hrms.recruitment.repository.*;
import com.hrms.recruitment.dto.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    public List<JobPostingResponse> listJobs() {
        return jobPostingRepository.findAll().stream()
                .map(JobPostingResponse::from)
                .toList();
    }

    public JobPostingResponse createJob(JobPostingRequest request) {
        JobPosting job = new JobPosting();
        job.setTitle(request.title());
        job.setDepartmentId(request.departmentId());
        job.setLocation(request.location());
        job.setEmploymentType(request.employmentType());
        job.setDescription(request.description());
        job.setRequirements(request.requirements());
        job.setStatus(request.status() != null ? request.status() : "Open");

        JobPosting saved = jobPostingRepository.save(job);
        return JobPostingResponse.from(saved);
    }

    public JobPostingResponse getJob(UUID id) {
        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + id));
        return JobPostingResponse.from(job);
    }
}
