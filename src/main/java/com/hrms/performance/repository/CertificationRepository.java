package com.hrms.performance.repository;

import com.hrms.performance.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, UUID> {
	Optional<Certification> findByVerificationId(String verificationId);
}
