package com.hrms.recruitment.controller;

import com.hrms.recruitment.service.*;
import com.hrms.recruitment.dto.*;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recruitment")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping("/jobs")
    public List<JobPostingResponse> listJobs() {
        return jobPostingService.listJobs();
    }

    @PostMapping("/jobs")
    public ResponseEntity<JobPostingResponse> createJob(@Valid @RequestBody JobPostingRequest request) {
        return ResponseEntity.ok(jobPostingService.createJob(request));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobPostingResponse> getJob(@PathVariable UUID id) {
        return ResponseEntity.ok(jobPostingService.getJob(id));
    }
}
