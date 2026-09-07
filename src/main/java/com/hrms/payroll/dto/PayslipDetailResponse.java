package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PayslipDetailResponse(UUID id, UUID employeeId, int month, int year, BigDecimal baseSalary,
                                    BigDecimal grossSalary, BigDecimal totalDeductions, BigDecimal totalTax,
                                    BigDecimal netSalary, int daysWorked, int daysAbsent, String status,
                                    UUID processedBy, LocalDateTime processedAt, LocalDateTime createdAt,
                                    LocalDateTime updatedAt, List<PayslipComponentResponse> components) {
}
