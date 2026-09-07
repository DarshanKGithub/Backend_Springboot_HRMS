package com.hrms.lifecycle.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExitRequest(UUID employeeId, String exitType, String reason, LocalDate noticePeriodEnd, LocalDate lastWorkingDay, BigDecimal settlementAmount, String status) {}
