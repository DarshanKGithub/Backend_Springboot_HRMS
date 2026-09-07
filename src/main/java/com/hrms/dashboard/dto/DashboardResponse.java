package com.hrms.dashboard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DashboardResponse(LocalDateTime generatedAt, DashboardFilters filters, DashboardKpis kpis, AttendanceSummary attendanceSummary, List<DepartmentBreakdown> departmentBreakdown) {
    public record DashboardFilters(LocalDate startDate, LocalDate endDate, UUID departmentId) {}
    public record DashboardKpis(long totalEmployees, long activeEmployees, long inactiveEmployees, long departmentsCount, long pendingApprovals) {}
    public record AttendanceSummary(String status, long present, long absent, long late) {}
    public record DepartmentBreakdown(UUID departmentId, String departmentName, long totalEmployees, long activeEmployees, long inactiveEmployees) {}
}
