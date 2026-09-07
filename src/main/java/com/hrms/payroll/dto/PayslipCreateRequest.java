package com.hrms.payroll.dto;

import java.util.UUID;

public record PayslipCreateRequest(UUID employeeId, int month, int year) {
}
