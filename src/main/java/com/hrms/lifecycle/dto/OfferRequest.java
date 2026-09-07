package com.hrms.lifecycle.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record OfferRequest(UUID employeeId, UUID candidateId, String title, BigDecimal salaryAmount, LocalDate joiningDate, String status, String notes) {}
