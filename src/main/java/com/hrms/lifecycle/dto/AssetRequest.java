package com.hrms.lifecycle.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssetRequest(String assetTag, String name, String category, String serialNumber, UUID employeeId, String status, LocalDateTime assignedOn, String notes) {}
