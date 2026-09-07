package com.hrms.tenancy.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plan_features")
public class PlanFeature {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "plan_id", nullable = false) private UUID planId;
    @Column(name = "feature_key", nullable = false, length = 120) private String featureKey;
    @Column(name = "is_included", nullable = false) private boolean included = true;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPlanId() { return planId; }
    public void setPlanId(UUID planId) { this.planId = planId; }
    public String getFeatureKey() { return featureKey; }
    public void setFeatureKey(String featureKey) { this.featureKey = featureKey; }
    public boolean isIncluded() { return included; }
    public void setIncluded(boolean included) { this.included = included; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
