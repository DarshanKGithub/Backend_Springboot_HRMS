package com.hrms.performance.controller;

import com.hrms.performance.dto.*;
import com.hrms.performance.service.LearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/performance")
public class LearningController {
    private final LearningService learningService;

    public LearningController(LearningService learningService) { this.learningService = learningService; }

    @PostMapping("/training/courses")
    public ResponseEntity<TrainingCourseResponse> createCourse(@RequestBody TrainingCourseRequest request) { return ResponseEntity.ok(learningService.createCourse(request)); }

    @GetMapping("/training/courses")
    public List<TrainingCourseResponse> listCourses() { return learningService.listCourses(); }

    @PostMapping("/training/enrollments")
    public ResponseEntity<TrainingEnrollmentResponse> enroll(@RequestBody TrainingEnrollmentRequest request) { return ResponseEntity.ok(learningService.enroll(request)); }

    @GetMapping("/training/enrollments/employee/{employeeId}")
    public List<TrainingEnrollmentResponse> listEmployeeTraining(@PathVariable UUID employeeId) { return learningService.listEmployeeTraining(employeeId); }

    @PutMapping("/training/enrollments/{id}")
    public ResponseEntity<TrainingEnrollmentResponse> updateTraining(@PathVariable UUID id, @RequestBody TrainingEnrollmentRequest request) { return ResponseEntity.ok(learningService.updateTraining(id, request)); }

    @PostMapping("/certifications")
    public ResponseEntity<CertificationResponse> createCertification(@RequestBody CertificationRequest request) { return ResponseEntity.ok(learningService.createCertification(request)); }

    @GetMapping("/certifications/verify/{verificationId}")
    public ResponseEntity<CertificationResponse> verify(@PathVariable String verificationId) { return ResponseEntity.ok(learningService.verify(verificationId)); }

    @GetMapping("/certifications/employee/{employeeId}")
    public List<CertificationResponse> listEmployeeCertifications(@PathVariable UUID employeeId) { return learningService.listEmployeeCertifications(employeeId); }

    @PutMapping("/certifications/{id}")
    public ResponseEntity<CertificationResponse> updateCertification(@PathVariable UUID id, @RequestBody CertificationRequest request) { return ResponseEntity.ok(learningService.updateCertification(id, request)); }

    @DeleteMapping("/certifications/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable UUID id) { learningService.deleteCertification(id); return ResponseEntity.noContent().build(); }
}
