package com.hrms.tenancy.repository;

import com.hrms.tenancy.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface PlanFeatureRepository extends JpaRepository<PlanFeature, UUID> {
	List<PlanFeature> findByPlanId(UUID planId);
}
