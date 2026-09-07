package com.hrms.survey.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "survey_responses")
public class SurveyResponse {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "survey_id", nullable = false) private UUID surveyId;
    @Column(name = "employee_id", nullable = false) private UUID employeeId;
    @Column(name = "submitted_at", nullable = false) private LocalDateTime submittedAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getSurveyId() { return surveyId; }
    public void setSurveyId(UUID surveyId) { this.surveyId = surveyId; }
    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
