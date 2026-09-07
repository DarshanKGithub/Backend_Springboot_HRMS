package com.hrms.performance.controller;

import com.hrms.performance.dto.*;
import com.hrms.performance.service.PerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) { this.performanceService = performanceService; }

    @PostMapping("/appraisals") public ResponseEntity<PerformanceAppraisalResponse> createAppraisal(@RequestBody PerformanceAppraisalRequest request) { return ResponseEntity.ok(performanceService.createAppraisal(request)); }
    @GetMapping("/appraisals/{id}") public ResponseEntity<PerformanceAppraisalResponse> getAppraisal(@PathVariable UUID id) { return ResponseEntity.ok(performanceService.getAppraisal(id)); }
    @GetMapping("/appraisals/employee/{employeeId}") public List<PerformanceAppraisalResponse> listAppraisals(@PathVariable UUID employeeId) { return performanceService.listAppraisals(employeeId); }
    @PutMapping("/appraisals/{id}") public ResponseEntity<PerformanceAppraisalResponse> updateAppraisal(@PathVariable UUID id, @RequestBody PerformanceAppraisalRequest request) { return ResponseEntity.ok(performanceService.updateAppraisal(id, request)); }
    @DeleteMapping("/appraisals/{id}") public ResponseEntity<Void> deleteAppraisal(@PathVariable UUID id) { performanceService.deleteAppraisal(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/goals") public ResponseEntity<GoalResponse> createGoal(@RequestBody GoalRequest request) { return ResponseEntity.ok(performanceService.createGoal(request)); }
    @GetMapping("/goals/{id}") public ResponseEntity<GoalResponse> getGoal(@PathVariable UUID id) { return ResponseEntity.ok(performanceService.getGoal(id)); }
    @GetMapping("/goals/employee/{employeeId}") public List<GoalResponse> listGoals(@PathVariable UUID employeeId, @RequestParam(required = false) String status) { return performanceService.listGoals(employeeId, status); }
    @PutMapping("/goals/{id}") public ResponseEntity<GoalResponse> updateGoal(@PathVariable UUID id, @RequestBody GoalRequest request) { return ResponseEntity.ok(performanceService.updateGoal(id, request)); }
    @DeleteMapping("/goals/{id}") public ResponseEntity<Void> deleteGoal(@PathVariable UUID id) { performanceService.deleteGoal(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/kpis") public ResponseEntity<KpiResponse> createKpi(@RequestBody KpiRequest request) { return ResponseEntity.ok(performanceService.createKpi(request)); }
    @GetMapping("/kpis/{id}") public ResponseEntity<KpiResponse> getKpi(@PathVariable UUID id) { return ResponseEntity.ok(performanceService.getKpi(id)); }
    @GetMapping("/kpis/employee/{employeeId}") public List<KpiResponse> listKpis(@PathVariable UUID employeeId, @RequestParam(required = false) String status) { return performanceService.listKpis(employeeId, status); }
    @GetMapping("/performance-score/{employeeId}") public BigDecimal performanceScore(@PathVariable UUID employeeId) { return performanceService.performanceScore(employeeId); }
    @PutMapping("/kpis/{id}") public ResponseEntity<KpiResponse> updateKpi(@PathVariable UUID id, @RequestBody KpiRequest request) { return ResponseEntity.ok(performanceService.updateKpi(id, request)); }
    @DeleteMapping("/kpis/{id}") public ResponseEntity<Void> deleteKpi(@PathVariable UUID id) { performanceService.deleteKpi(id); return ResponseEntity.noContent().build(); }
}
