package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PayslipComponentResponse(UUID id, String componentName, String componentType, BigDecimal amount) {
}
