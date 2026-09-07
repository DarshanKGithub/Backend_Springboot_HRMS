package com.hrms.performance.service;

import com.hrms.performance.dto.*;
import com.hrms.performance.entity.*;
import com.hrms.performance.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PerformanceService {
    private final PerformanceAppraisalRepository appraisalRepository;
    private final GoalRepository goalRepository;
    private final KpiRepository kpiRepository;

    public PerformanceService(PerformanceAppraisalRepository appraisalRepository, GoalRepository goalRepository, KpiRepository kpiRepository) {
        this.appraisalRepository = appraisalRepository;
        this.goalRepository = goalRepository;
        this.kpiRepository = kpiRepository;
    }

    public PerformanceAppraisalResponse createAppraisal(PerformanceAppraisalRequest request) {
        PerformanceAppraisal item = new PerformanceAppraisal();
        apply(item, request);
        return toResponse(appraisalRepository.save(item));
    }

    public PerformanceAppraisalResponse getAppraisal(UUID id) {
        return appraisalRepository.findById(id).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("Appraisal not found: " + id));
    }

    public List<PerformanceAppraisalResponse> listAppraisals(UUID employeeId) {
        return appraisalRepository.findAll().stream().filter(item -> employeeId == null || employeeId.equals(item.getEmployeeId())).map(this::toResponse).toList();
    }

    public PerformanceAppraisalResponse updateAppraisal(UUID id, PerformanceAppraisalRequest request) {
        PerformanceAppraisal item = appraisalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Appraisal not found: " + id));
        apply(item, request);
        return toResponse(appraisalRepository.save(item));
    }

    public void deleteAppraisal(UUID id) { appraisalRepository.deleteById(id); }

    public GoalResponse createGoal(GoalRequest request) {
        Goal item = new Goal();
        apply(item, request);
        return toResponse(goalRepository.save(item));
    }

    public GoalResponse getGoal(UUID id) { return goalRepository.findById(id).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("Goal not found: " + id)); }

    public List<GoalResponse> listGoals(UUID employeeId, String status) {
        return goalRepository.findAll().stream().filter(item -> employeeId == null || employeeId.equals(item.getEmployeeId())).filter(item -> status == null || status.equals(item.getStatus())).map(this::toResponse).toList();
    }

    public GoalResponse updateGoal(UUID id, GoalRequest request) {
        Goal item = goalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Goal not found: " + id));
        apply(item, request);
        return toResponse(goalRepository.save(item));
    }

    public void deleteGoal(UUID id) { goalRepository.deleteById(id); }

    public KpiResponse createKpi(KpiRequest request) {
        Kpi item = new Kpi();
        apply(item, request);
        return toResponse(kpiRepository.save(item));
    }

    public KpiResponse getKpi(UUID id) { return kpiRepository.findById(id).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("KPI not found: " + id)); }

    public List<KpiResponse> listKpis(UUID employeeId, String status) {
        return kpiRepository.findAll().stream().filter(item -> employeeId == null || employeeId.equals(item.getEmployeeId())).filter(item -> status == null || status.equals(item.getStatus())).map(this::toResponse).toList();
    }

    public KpiResponse updateKpi(UUID id, KpiRequest request) {
        Kpi item = kpiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("KPI not found: " + id));
        apply(item, request);
        return toResponse(kpiRepository.save(item));
    }

    public void deleteKpi(UUID id) { kpiRepository.deleteById(id); }

    public BigDecimal performanceScore(UUID employeeId) {
        List<Kpi> items = kpiRepository.findAll().stream().filter(item -> employeeId.equals(item.getEmployeeId())).toList();
        BigDecimal totalWeight = items.stream().map(Kpi::getWeightage).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalWeight.signum() == 0) return BigDecimal.ZERO;
        BigDecimal weighted = items.stream().map(item -> {
            if (item.getTargetValue() == null || item.getTargetValue().signum() == 0 || item.getAchievedValue() == null) return BigDecimal.ZERO;
            return item.getAchievedValue().divide(item.getTargetValue(), 4, java.math.RoundingMode.HALF_UP).multiply(item.getWeightage());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return weighted.divide(totalWeight, 2, java.math.RoundingMode.HALF_UP);
    }

    private void apply(PerformanceAppraisal item, PerformanceAppraisalRequest request) {
        item.setEmployeeId(request.employeeId()); item.setReviewerId(request.reviewerId()); item.setReviewPeriod(request.reviewPeriod());
        if (request.status() != null) item.setStatus(request.status()); item.setRating(request.rating()); item.setGoalsAchieved(request.goalsAchieved());
        item.setAreasForImprovement(request.areasForImprovement()); item.setComments(request.comments()); item.setReviewDate(request.reviewDate()); item.setNextReviewDate(request.nextReviewDate());
    }
    private void apply(Goal item, GoalRequest request) {
        item.setEmployeeId(request.employeeId()); item.setAppraisalId(request.appraisalId()); item.setTitle(request.title()); item.setDescription(request.description());
        if (request.status() != null) item.setStatus(request.status()); item.setStartDate(request.startDate()); item.setEndDate(request.endDate()); item.setTargetValue(request.targetValue()); item.setAchievedValue(request.achievedValue());
        if (request.achievementPercentage() != null) item.setAchievementPercentage(request.achievementPercentage());
    }
    private void apply(Kpi item, KpiRequest request) {
        item.setEmployeeId(request.employeeId()); item.setGoalId(request.goalId()); item.setTitle(request.title()); item.setDescription(request.description());
        if (request.status() != null) item.setStatus(request.status()); item.setTargetValue(request.targetValue()); item.setAchievedValue(request.achievedValue()); item.setWeightage(request.weightage()); item.setStartDate(request.startDate()); item.setEndDate(request.endDate());
    }
    private PerformanceAppraisalResponse toResponse(PerformanceAppraisal item) { return new PerformanceAppraisalResponse(item.getId(), item.getEmployeeId(), item.getReviewerId(), item.getReviewPeriod(), item.getStatus(), item.getRating(), item.getGoalsAchieved(), item.getAreasForImprovement(), item.getComments(), item.getReviewDate(), item.getNextReviewDate()); }
    private GoalResponse toResponse(Goal item) { return new GoalResponse(item.getId(), item.getEmployeeId(), item.getAppraisalId(), item.getTitle(), item.getDescription(), item.getStatus(), item.getStartDate(), item.getEndDate(), item.getTargetValue(), item.getAchievedValue(), item.getAchievementPercentage()); }
    private KpiResponse toResponse(Kpi item) { return new KpiResponse(item.getId(), item.getEmployeeId(), item.getGoalId(), item.getTitle(), item.getDescription(), item.getStatus(), item.getTargetValue(), item.getAchievedValue(), item.getWeightage(), item.getStartDate(), item.getEndDate()); }
}
