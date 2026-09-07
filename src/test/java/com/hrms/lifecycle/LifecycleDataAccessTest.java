package com.hrms.lifecycle;

import com.hrms.lifecycle.entity.*;
import com.hrms.lifecycle.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LifecycleDataAccessTest {

    @Autowired
    private OfferLetterRepository offerLetterRepository;

    @Autowired
    private OnboardingPlanRepository onboardingPlanRepository;

    @Autowired
    private ExitCaseRepository exitCaseRepository;

    @Autowired
    private AssetInventoryRepository assetInventoryRepository;

    @Test
    void shouldSaveAndLoadLifecycleEntities() {
        OfferLetter offerLetter = new OfferLetter();
        offerLetter.setEmployeeId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        offerLetter.setCandidateId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
        offerLetter.setTitle("Senior Java Engineer");
        offerLetter.setSalaryAmount(new BigDecimal("180000.00"));
        offerLetter.setJoiningDate(LocalDate.of(2026, 10, 1));
        offerLetter.setStatus("Draft");
        offerLetter.setNotes("Offer in progress");

        OfferLetter savedOffer = offerLetterRepository.save(offerLetter);

        OnboardingPlan onboardingPlan = new OnboardingPlan();
        onboardingPlan.setEmployeeId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        onboardingPlan.setProbationEndDate(LocalDate.of(2027, 3, 31));
        onboardingPlan.setChecklist("[\"Laptop\", \"System access\"]");
        onboardingPlan.setOwnerId(UUID.fromString("66666666-6666-6666-6666-666666666666"));
        onboardingPlan.setStatus("Initiated");

        OnboardingPlan savedOnboarding = onboardingPlanRepository.save(onboardingPlan);

        ExitCase exitCase = new ExitCase();
        exitCase.setEmployeeId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        exitCase.setExitType("Resignation");
        exitCase.setReason("Career growth");
        exitCase.setNoticePeriodEnd(LocalDate.of(2026, 9, 30));
        exitCase.setLastWorkingDay(LocalDate.of(2026, 9, 30));
        exitCase.setSettlementAmount(new BigDecimal("25000.00"));
        exitCase.setStatus("Requested");

        ExitCase savedExit = exitCaseRepository.save(exitCase);

        AssetInventory assetInventory = new AssetInventory();
        assetInventory.setAssetTag("ASSET-001");
        assetInventory.setName("MacBook Pro");
        assetInventory.setCategory("Laptop");
        assetInventory.setSerialNumber("MBP-2026-01");
        assetInventory.setEmployeeId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        assetInventory.setStatus("Assigned");
        assetInventory.setAssignedOn(LocalDateTime.of(2026, 9, 1, 10, 0));
        assetInventory.setNotes("Assigned to new joiner");

        AssetInventory savedAsset = assetInventoryRepository.save(assetInventory);

        assertThat(savedOffer.getId()).isNotNull();
        assertThat(offerLetterRepository.findById(savedOffer.getId())).isPresent();
        assertThat(savedOnboarding.getId()).isNotNull();
        assertThat(onboardingPlanRepository.findById(savedOnboarding.getId())).isPresent();
        assertThat(savedExit.getId()).isNotNull();
        assertThat(exitCaseRepository.findById(savedExit.getId())).isPresent();
        assertThat(savedAsset.getId()).isNotNull();
        assertThat(assetInventoryRepository.findById(savedAsset.getId())).isPresent();
    }
}
