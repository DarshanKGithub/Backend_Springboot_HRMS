package com.hrms.performance.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CertificationRequest(UUID employeeId, String verificationId, String name, String issuingAuthority, LocalDate issueDate, LocalDate expiryDate) {
}
