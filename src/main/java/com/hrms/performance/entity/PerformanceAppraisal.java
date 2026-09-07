package com.hrms.performance.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "performance_appraisals")
public class PerformanceAppraisal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "reviewer_id")
    private UUID reviewerId;

    @Column(name = "review_period", nullable = false, length = 100)
    private String reviewPeriod;

    @Column(nullable = false, length = 30)
    private String status = "Draft";

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(name = "goals_achieved", length = 2000)
    private String goalsAchieved;

    @Column(name = "areas_for_improvement", length = 2000)
    private String areasForImprovement;

    @Column(length = 2000)
    private String comments;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;

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

    public UUID getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(UUID reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getGoalsAchieved() {
        return goalsAchieved;
    }

    public void setGoalsAchieved(String goalsAchieved) {
        this.goalsAchieved = goalsAchieved;
    }

    public String getAreasForImprovement() {
        return areasForImprovement;
    }

    public void setAreasForImprovement(String areasForImprovement) {
        this.areasForImprovement = areasForImprovement;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }
}
