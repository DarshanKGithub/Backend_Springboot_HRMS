package com.hrms.org.repository;

import com.hrms.org.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
	List<AuditLog> findByObjectTypeContainingIgnoreCaseAndActorId(String objectType, UUID actorId);
}
