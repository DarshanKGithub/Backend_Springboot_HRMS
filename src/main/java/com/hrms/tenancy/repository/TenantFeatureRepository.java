package com.hrms.tenancy.repository;

import com.hrms.tenancy.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface TenantFeatureRepository extends JpaRepository<TenantFeature, UUID> {
	List<TenantFeature> findByTenantId(UUID tenantId);
}
