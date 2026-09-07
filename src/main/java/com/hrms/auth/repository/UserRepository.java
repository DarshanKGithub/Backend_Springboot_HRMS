package com.hrms.auth.repository;

import com.hrms.auth.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findByTenantId(UUID tenantId);
}
