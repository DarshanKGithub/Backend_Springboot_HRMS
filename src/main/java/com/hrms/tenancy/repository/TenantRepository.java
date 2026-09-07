package com.hrms.tenancy.repository;

import com.hrms.tenancy.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
