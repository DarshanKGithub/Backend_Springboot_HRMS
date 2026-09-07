package com.hrms.lifecycle.dto;

import java.time.LocalDate;
import java.util.UUID;

public record OnboardingRequest(UUID employeeId, LocalDate probationEndDate, String checklist, UUID ownerId, String status) {}
