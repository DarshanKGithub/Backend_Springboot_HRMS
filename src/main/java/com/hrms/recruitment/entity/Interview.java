package com.hrms.recruitment.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interviews")
public class Interview {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "application_id", nullable = false) private UUID applicationId;
    @Column(name = "interviewer_id", nullable = false) private UUID interviewerId;
    @Column(name = "scheduled_at", nullable = false) private LocalDateTime scheduledAt;
    @Column(name = "meeting_link", length = 500) private String meetingLink;
    @Column(nullable = false, length = 30) private String status = "Scheduled";
    @Column(length = 2000) private String feedback;
    @Column private Integer rating;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getApplicationId() { return applicationId; }
    public void setApplicationId(UUID applicationId) { this.applicationId = applicationId; }
    public UUID getInterviewerId() { return interviewerId; }
    public void setInterviewerId(UUID interviewerId) { this.interviewerId = interviewerId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
