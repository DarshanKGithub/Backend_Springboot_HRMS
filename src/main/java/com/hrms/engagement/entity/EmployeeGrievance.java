package com.hrms.engagement.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_grievances")
public class EmployeeGrievance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "against_employee_id")
    private UUID againstEmployeeId;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, length = 30)
    private String status = "Submitted";

    @Column(name = "investigator_id")
    private UUID investigatorId;

    @Column(name = "investigation_notes", length = 2000)
    private String investigationNotes;

    @Column(name = "meeting_scheduled_at")
    private LocalDateTime meetingScheduledAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getAgainstEmployeeId() {
        return againstEmployeeId;
    }

    public void setAgainstEmployeeId(UUID againstEmployeeId) {
        this.againstEmployeeId = againstEmployeeId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(UUID investigatorId) {
        this.investigatorId = investigatorId;
    }

    public String getInvestigationNotes() {
        return investigationNotes;
    }

    public void setInvestigationNotes(String investigationNotes) {
        this.investigationNotes = investigationNotes;
    }

    public LocalDateTime getMeetingScheduledAt() {
        return meetingScheduledAt;
    }

    public void setMeetingScheduledAt(LocalDateTime meetingScheduledAt) {
        this.meetingScheduledAt = meetingScheduledAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
