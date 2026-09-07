package com.hrms.tenancy.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feature_package_features")
public class FeaturePackageFeature {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "package_id", nullable = false) private UUID packageId;
    @Column(name = "feature_key", nullable = false, length = 120) private String featureKey;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPackageId() { return packageId; }
    public void setPackageId(UUID packageId) { this.packageId = packageId; }
    public String getFeatureKey() { return featureKey; }
    public void setFeatureKey(String featureKey) { this.featureKey = featureKey; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
