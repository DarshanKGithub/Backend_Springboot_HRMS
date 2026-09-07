package com.hrms.engagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeDocumentResponse(UUID id, UUID employeeId, String documentType, String fileName, String filePath, UUID uploadedBy, LocalDateTime uploadedAt) {}
