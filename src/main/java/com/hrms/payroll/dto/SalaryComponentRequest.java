package com.hrms.payroll.dto;

public record SalaryComponentRequest(String name, String componentType, String description, boolean active) {
}
