package com.hrms.performance.service;

import com.hrms.performance.dto.*;
import com.hrms.performance.entity.*;
import com.hrms.performance.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LearningService {
    private final TrainingCourseRepository courseRepository;
    private final EmployeeTrainingRepository trainingRepository;
    private final CertificationRepository certificationRepository;

    public LearningService(TrainingCourseRepository courseRepository, EmployeeTrainingRepository trainingRepository, CertificationRepository certificationRepository) {
        this.courseRepository = courseRepository;
        this.trainingRepository = trainingRepository;
        this.certificationRepository = certificationRepository;
    }

    public TrainingCourseResponse createCourse(TrainingCourseRequest request) {
        TrainingCourse course = new TrainingCourse();
        apply(course, request);
        return toResponse(courseRepository.save(course));
    }
    public List<TrainingCourseResponse> listCourses() { return courseRepository.findAll().stream().map(this::toResponse).toList(); }
    public TrainingEnrollmentResponse enroll(TrainingEnrollmentRequest request) {
        EmployeeTraining training = new EmployeeTraining();
        apply(training, request);
        return toResponse(trainingRepository.save(training));
    }
    public List<TrainingEnrollmentResponse> listEmployeeTraining(UUID employeeId) { return trainingRepository.findAll().stream().filter(item -> employeeId.equals(item.getEmployeeId())).map(this::toResponse).toList(); }
    public TrainingEnrollmentResponse updateTraining(UUID id, TrainingEnrollmentRequest request) {
        EmployeeTraining training = trainingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Training enrollment not found: " + id));
        apply(training, request);
        return toResponse(trainingRepository.save(training));
    }
    public CertificationResponse createCertification(CertificationRequest request) {
        Certification certification = new Certification();
        apply(certification, request);
        return toResponse(certificationRepository.save(certification));
    }
    public CertificationResponse verify(String verificationId) { return certificationRepository.findByVerificationId(verificationId.toUpperCase()).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("Certification not found: " + verificationId)); }
    public List<CertificationResponse> listEmployeeCertifications(UUID employeeId) { return certificationRepository.findAll().stream().filter(item -> employeeId.equals(item.getEmployeeId())).map(this::toResponse).toList(); }
    public CertificationResponse updateCertification(UUID id, CertificationRequest request) {
        Certification certification = certificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Certification not found: " + id));
        apply(certification, request);
        return toResponse(certificationRepository.save(certification));
    }
    public void deleteCertification(UUID id) { certificationRepository.deleteById(id); }

    private void apply(TrainingCourse item, TrainingCourseRequest request) { item.setTitle(request.title()); item.setDescription(request.description()); item.setInstructor(request.instructor()); item.setDurationHours(request.durationHours()); }
    private void apply(EmployeeTraining item, TrainingEnrollmentRequest request) { item.setEmployeeId(request.employeeId()); item.setCourseId(request.courseId()); if (request.status() != null) item.setStatus(request.status()); item.setCompletionDate(request.completionDate()); }
    private void apply(Certification item, CertificationRequest request) { item.setEmployeeId(request.employeeId()); item.setVerificationId(request.verificationId().toUpperCase()); item.setName(request.name()); item.setIssuingAuthority(request.issuingAuthority()); item.setIssueDate(request.issueDate()); item.setExpiryDate(request.expiryDate()); }
    private TrainingCourseResponse toResponse(TrainingCourse item) { return new TrainingCourseResponse(item.getId(), item.getTitle(), item.getDescription(), item.getInstructor(), item.getDurationHours()); }
    private TrainingEnrollmentResponse toResponse(EmployeeTraining item) { return new TrainingEnrollmentResponse(item.getId(), item.getEmployeeId(), item.getCourseId(), item.getStatus(), item.getCompletionDate()); }
    private CertificationResponse toResponse(Certification item) { boolean expired = item.getExpiryDate() != null && item.getExpiryDate().isBefore(LocalDate.now()); return new CertificationResponse(item.getId(), item.getEmployeeId(), item.getVerificationId(), item.getName(), item.getIssuingAuthority(), item.getIssueDate(), item.getExpiryDate(), expired, !expired); }
}
