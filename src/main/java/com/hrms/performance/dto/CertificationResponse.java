package com.hrms.performance.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CertificationResponse(UUID id, UUID employeeId, String verificationId, String name, String issuingAuthority, LocalDate issueDate, LocalDate expiryDate, boolean expired, boolean valid) {
}
