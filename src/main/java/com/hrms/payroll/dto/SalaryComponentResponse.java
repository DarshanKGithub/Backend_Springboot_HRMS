package com.hrms.payroll.dto;

import java.util.UUID;

public record SalaryComponentResponse(UUID id, String name, String componentType, String description, boolean active) {
}
