package com.hrms;

import com.hrms.auth.entity.User;
import com.hrms.auth.repository.UserRepository;
import com.hrms.org.entity.AuditLog;
import com.hrms.org.repository.AuditLogRepository;
import com.hrms.org.entity.Designation;
import com.hrms.org.repository.DesignationRepository;
import com.hrms.recruitment.entity.Interview;
import com.hrms.recruitment.repository.InterviewRepository;
import com.hrms.tenancy.entity.*;
import com.hrms.tenancy.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RemainingModelsDataAccessTest {

    @Autowired private UserRepository userRepository;
    @Autowired private DesignationRepository designationRepository;
    @Autowired private AuditLogRepository auditLogRepository;
    @Autowired private PlanRepository planRepository;
    @Autowired private PlanFeatureRepository planFeatureRepository;
    @Autowired private FeaturePackageRepository featurePackageRepository;
    @Autowired private FeaturePackageFeatureRepository featurePackageFeatureRepository;
    @Autowired private TenantFeatureRepository tenantFeatureRepository;
    @Autowired private InterviewRepository interviewRepository;

    @Test
    void shouldPersistRemainingBackendModels() {
        User user = new User();
        user.setFullName("Platform Admin");
        user.setEmail("platform-admin@example.com");
        user.setRole("Admin");
        user.setPasswordHash("encoded-password");
        User savedUser = userRepository.save(user);

        Designation designation = new Designation();
        designation.setName("Senior Engineer");
        Designation savedDesignation = designationRepository.save(designation);

        AuditLog auditLog = new AuditLog();
        auditLog.setActorId(savedUser.getId());
        auditLog.setAction("created");
        auditLog.setObjectType("designation");
        auditLog.setObjectId(savedDesignation.getId());
        auditLog.setData("{\"source\":\"test\"}");
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);

        Plan plan = new Plan();
        plan.setName("Enterprise");
        plan.setPriceCents(99900);
        Plan savedPlan = planRepository.save(plan);

        PlanFeature planFeature = new PlanFeature();
        planFeature.setPlanId(savedPlan.getId());
        planFeature.setFeatureKey("advanced_payroll");
        PlanFeature savedPlanFeature = planFeatureRepository.save(planFeature);

        FeaturePackage featurePackage = new FeaturePackage();
        featurePackage.setName("Recruitment add-on");
        featurePackage.setPriceCents(25000);
        FeaturePackage savedPackage = featurePackageRepository.save(featurePackage);

        FeaturePackageFeature packageFeature = new FeaturePackageFeature();
        packageFeature.setPackageId(savedPackage.getId());
        packageFeature.setFeatureKey("interviews");
        FeaturePackageFeature savedPackageFeature = featurePackageFeatureRepository.save(packageFeature);

        TenantFeature tenantFeature = new TenantFeature();
        tenantFeature.setTenantId(UUID.randomUUID());
        tenantFeature.setFeatureKey("advanced_payroll");
        TenantFeature savedTenantFeature = tenantFeatureRepository.save(tenantFeature);

        Interview interview = new Interview();
        interview.setApplicationId(UUID.randomUUID());
        interview.setInterviewerId(savedUser.getId());
        interview.setScheduledAt(LocalDateTime.of(2026, 9, 10, 10, 0));
        interview.setRating(5);
        Interview savedInterview = interviewRepository.save(interview);

        assertThat(userRepository.findById(savedUser.getId())).isPresent();
        assertThat(auditLogRepository.findById(savedAuditLog.getId())).isPresent();
        assertThat(planFeatureRepository.findById(savedPlanFeature.getId())).isPresent();
        assertThat(featurePackageFeatureRepository.findById(savedPackageFeature.getId())).isPresent();
        assertThat(tenantFeatureRepository.findById(savedTenantFeature.getId())).isPresent();
        assertThat(interviewRepository.findById(savedInterview.getId())).isPresent();
    }
}
