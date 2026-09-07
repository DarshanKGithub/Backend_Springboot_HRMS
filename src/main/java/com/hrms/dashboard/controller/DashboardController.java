package com.hrms.dashboard.controller;

import com.hrms.dashboard.dto.DashboardResponse;
import com.hrms.dashboard.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    public DashboardController(DashboardService dashboardService) { this.dashboardService = dashboardService; }
    @GetMapping("/summary") public DashboardResponse summary(@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate, @RequestParam(required = false) UUID departmentId) { return dashboardService.summary(startDate, endDate, departmentId); }
}
