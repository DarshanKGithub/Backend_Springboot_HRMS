package com.hrms.lifecycle.service;

import com.hrms.lifecycle.dto.*;
import com.hrms.lifecycle.entity.*;
import com.hrms.lifecycle.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LifecycleService {
    private final OfferLetterRepository offerRepository;
    private final OnboardingPlanRepository onboardingRepository;
    private final ExitCaseRepository exitRepository;
    private final AssetInventoryRepository assetRepository;

    public LifecycleService(OfferLetterRepository offerRepository, OnboardingPlanRepository onboardingRepository, ExitCaseRepository exitRepository, AssetInventoryRepository assetRepository) {
        this.offerRepository = offerRepository; this.onboardingRepository = onboardingRepository; this.exitRepository = exitRepository; this.assetRepository = assetRepository;
    }
    public LifecycleSummaryResponse summary() { return new LifecycleSummaryResponse(offerRepository.count(), onboardingRepository.count(), exitRepository.count(), assetRepository.count()); }
    public List<OfferLetter> offers() { return offerRepository.findAll(); }
    public OfferLetter createOffer(OfferRequest request) { OfferLetter item = new OfferLetter(); apply(item, request); return offerRepository.save(item); }
    public OfferLetter updateOffer(UUID id, OfferRequest request) { OfferLetter item = offerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Offer not found: " + id)); apply(item, request); return offerRepository.save(item); }
    public List<OnboardingPlan> onboarding() { return onboardingRepository.findAll(); }
    public OnboardingPlan createOnboarding(OnboardingRequest request) { OnboardingPlan item = new OnboardingPlan(); apply(item, request); return onboardingRepository.save(item); }
    public OnboardingPlan updateOnboarding(UUID id, OnboardingRequest request) { OnboardingPlan item = onboardingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Onboarding plan not found: " + id)); apply(item, request); return onboardingRepository.save(item); }
    public List<ExitCase> exits() { return exitRepository.findAll(); }
    public ExitCase createExit(ExitRequest request) { ExitCase item = new ExitCase(); apply(item, request); return exitRepository.save(item); }
    public ExitCase updateExit(UUID id, ExitRequest request) { ExitCase item = exitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exit case not found: " + id)); apply(item, request); return exitRepository.save(item); }
    public List<AssetInventory> assets() { return assetRepository.findAll(); }
    public AssetInventory createAsset(AssetRequest request) { AssetInventory item = new AssetInventory(); apply(item, request); return assetRepository.save(item); }
    public AssetInventory updateAsset(UUID id, AssetRequest request) { AssetInventory item = assetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Asset not found: " + id)); apply(item, request); return assetRepository.save(item); }
    private void apply(OfferLetter i, OfferRequest r) { i.setEmployeeId(r.employeeId()); i.setCandidateId(r.candidateId()); i.setTitle(r.title()); i.setSalaryAmount(r.salaryAmount()); i.setJoiningDate(r.joiningDate()); if (r.status()!=null) i.setStatus(r.status()); i.setNotes(r.notes()); }
    private void apply(OnboardingPlan i, OnboardingRequest r) { i.setEmployeeId(r.employeeId()); i.setProbationEndDate(r.probationEndDate()); if (r.checklist()!=null) i.setChecklist(r.checklist()); i.setOwnerId(r.ownerId()); if (r.status()!=null) i.setStatus(r.status()); }
    private void apply(ExitCase i, ExitRequest r) { i.setEmployeeId(r.employeeId()); i.setExitType(r.exitType()); i.setReason(r.reason()); i.setNoticePeriodEnd(r.noticePeriodEnd()); i.setLastWorkingDay(r.lastWorkingDay()); if (r.settlementAmount()!=null) i.setSettlementAmount(r.settlementAmount()); if (r.status()!=null) i.setStatus(r.status()); }
    private void apply(AssetInventory i, AssetRequest r) { i.setAssetTag(r.assetTag()); i.setName(r.name()); i.setCategory(r.category()); i.setSerialNumber(r.serialNumber()); i.setEmployeeId(r.employeeId()); if (r.status()!=null) i.setStatus(r.status()); i.setAssignedOn(r.assignedOn()); i.setNotes(r.notes()); }
}
