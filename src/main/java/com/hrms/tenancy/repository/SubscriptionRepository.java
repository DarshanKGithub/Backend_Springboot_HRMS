package com.hrms.tenancy.repository;

import com.hrms.tenancy.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
	List<Subscription> findByTenantId(UUID tenantId);
	java.util.Optional<Subscription> findByExternalOrderId(String externalOrderId);
}
