package com.hrms.performance;

import com.hrms.performance.entity.*;
import com.hrms.performance.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PerformanceDataAccessTest {

    @Autowired
    private PerformanceAppraisalRepository performanceAppraisalRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private TrainingCourseRepository trainingCourseRepository;

    @Autowired
    private EmployeeTrainingRepository employeeTrainingRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    void shouldSaveAndLoadPerformanceEntities() {
        PerformanceAppraisal appraisal = new PerformanceAppraisal();
        appraisal.setEmployeeId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
        appraisal.setReviewerId(UUID.fromString("88888888-8888-8888-8888-888888888888"));
        appraisal.setReviewPeriod("Q1 2026");
        appraisal.setStatus("Draft");
        appraisal.setRating(new BigDecimal("4.5"));
        appraisal.setGoalsAchieved("Delivered core features");
        appraisal.setAreasForImprovement("Improve communication");
        appraisal.setComments("Solid contribution");
        appraisal.setReviewDate(LocalDate.of(2026, 3, 31));
        appraisal.setNextReviewDate(LocalDate.of(2026, 6, 30));

        PerformanceAppraisal savedAppraisal = performanceAppraisalRepository.save(appraisal);

        Goal goal = new Goal();
        goal.setEmployeeId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
        goal.setAppraisalId(savedAppraisal.getId());
        goal.setTitle("Improve backend throughput");
        goal.setDescription("Reduce API latency");
        goal.setStatus("Active");
        goal.setStartDate(LocalDate.of(2026, 1, 1));
        goal.setEndDate(LocalDate.of(2026, 3, 31));
        goal.setTargetValue(new BigDecimal("95.00"));
        goal.setAchievedValue(new BigDecimal("88.00"));
        goal.setAchievementPercentage(new BigDecimal("88.00"));

        Goal savedGoal = goalRepository.save(goal);

        Kpi kpi = new Kpi();
        kpi.setEmployeeId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
        kpi.setGoalId(savedGoal.getId());
        kpi.setTitle("API reliability");
        kpi.setDescription("Maintain 99.9% uptime");
        kpi.setStatus("Active");
        kpi.setTargetValue(new BigDecimal("99.90"));
        kpi.setAchievedValue(new BigDecimal("99.50"));
        kpi.setWeightage(new BigDecimal("30.00"));
        kpi.setStartDate(LocalDate.of(2026, 1, 1));
        kpi.setEndDate(LocalDate.of(2026, 3, 31));

        Kpi savedKpi = kpiRepository.save(kpi);

        TrainingCourse course = new TrainingCourse();
        course.setTitle("Spring Boot Advanced");
        course.setDescription("Advanced backend architecture");
        course.setInstructor("Aarav");
        course.setDurationHours(new BigDecimal("18.00"));

        TrainingCourse savedCourse = trainingCourseRepository.save(course);

        EmployeeTraining employeeTraining = new EmployeeTraining();
        employeeTraining.setEmployeeId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
        employeeTraining.setCourseId(savedCourse.getId());
        employeeTraining.setStatus("In Progress");
        employeeTraining.setCompletionDate(LocalDate.of(2026, 4, 10));

        EmployeeTraining savedTraining = employeeTrainingRepository.save(employeeTraining);

        Certification certification = new Certification();
        certification.setVerificationId("CERT-001");
        certification.setEmployeeId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
        certification.setName("Oracle Certified Associate");
        certification.setIssuingAuthority("Oracle");
        certification.setIssueDate(LocalDate.of(2025, 6, 15));
        certification.setExpiryDate(LocalDate.of(2028, 6, 15));

        Certification savedCertification = certificationRepository.save(certification);

        assertThat(savedAppraisal.getId()).isNotNull();
        assertThat(performanceAppraisalRepository.findById(savedAppraisal.getId())).isPresent();
        assertThat(savedGoal.getId()).isNotNull();
        assertThat(goalRepository.findById(savedGoal.getId())).isPresent();
        assertThat(savedKpi.getId()).isNotNull();
        assertThat(kpiRepository.findById(savedKpi.getId())).isPresent();
        assertThat(savedCourse.getId()).isNotNull();
        assertThat(trainingCourseRepository.findById(savedCourse.getId())).isPresent();
        assertThat(savedTraining.getId()).isNotNull();
        assertThat(employeeTrainingRepository.findById(savedTraining.getId())).isPresent();
        assertThat(savedCertification.getId()).isNotNull();
        assertThat(certificationRepository.findById(savedCertification.getId())).isPresent();
    }
}
