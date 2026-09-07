package com.hrms.recruitment;

import com.hrms.recruitment.entity.*;
import com.hrms.recruitment.repository.*;
import com.hrms.recruitment.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecruitmentDataAccessTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Test
    void shouldSaveAndLoadRecruitmentEntities() {
        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("Java Backend Engineer");
        jobPosting.setDepartmentId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        jobPosting.setLocation("Bengaluru");
        jobPosting.setEmploymentType("Full-time");
        jobPosting.setDescription("Build backend services");
        jobPosting.setRequirements("Java, Spring Boot");
        jobPosting.setStatus("Open");

        JobPosting savedJob = jobPostingRepository.save(jobPosting);

        Candidate candidate = new Candidate();
        candidate.setFirstName("Asha");
        candidate.setLastName("Nair");
        candidate.setEmail("asha.nair@example.com");
        candidate.setPhone("9999999999");
        candidate.setResumeUrl("https://example.com/resume.pdf");
        candidate.setSource("LinkedIn");

        Candidate savedCandidate = candidateRepository.save(candidate);

        JobApplication application = new JobApplication();
        application.setJobId(savedJob.getId());
        application.setCandidateId(savedCandidate.getId());
        application.setStatus("Applied");

        JobApplication savedApplication = jobApplicationRepository.save(application);

        assertThat(savedJob.getId()).isNotNull();
        assertThat(jobPostingRepository.findById(savedJob.getId())).isPresent();
        assertThat(savedCandidate.getId()).isNotNull();
        assertThat(candidateRepository.findById(savedCandidate.getId())).isPresent();
        assertThat(savedApplication.getId()).isNotNull();
        assertThat(jobApplicationRepository.findById(savedApplication.getId())).isPresent();
    }
}
