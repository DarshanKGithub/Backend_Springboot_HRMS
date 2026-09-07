package com.hrms.lifecycle.controller;

import com.hrms.lifecycle.dto.*;
import com.hrms.lifecycle.entity.*;
import com.hrms.lifecycle.service.LifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lifecycle")
public class LifecycleController {
    private final LifecycleService service;
    public LifecycleController(LifecycleService service) { this.service = service; }
    @GetMapping("/summary") public LifecycleSummaryResponse summary() { return service.summary(); }
    @GetMapping("/offers") public List<OfferLetter> offers() { return service.offers(); }
    @PostMapping("/offers") public ResponseEntity<OfferLetter> createOffer(@RequestBody OfferRequest request) { return ResponseEntity.ok(service.createOffer(request)); }
    @PutMapping("/offers/{id}") public ResponseEntity<OfferLetter> updateOffer(@PathVariable UUID id, @RequestBody OfferRequest request) { return ResponseEntity.ok(service.updateOffer(id, request)); }
    @GetMapping("/onboarding") public List<OnboardingPlan> onboarding() { return service.onboarding(); }
    @PostMapping("/onboarding") public ResponseEntity<OnboardingPlan> createOnboarding(@RequestBody OnboardingRequest request) { return ResponseEntity.ok(service.createOnboarding(request)); }
    @PutMapping("/onboarding/{id}") public ResponseEntity<OnboardingPlan> updateOnboarding(@PathVariable UUID id, @RequestBody OnboardingRequest request) { return ResponseEntity.ok(service.updateOnboarding(id, request)); }
    @GetMapping("/exits") public List<ExitCase> exits() { return service.exits(); }
    @PostMapping("/exits") public ResponseEntity<ExitCase> createExit(@RequestBody ExitRequest request) { return ResponseEntity.ok(service.createExit(request)); }
    @PutMapping("/exits/{id}") public ResponseEntity<ExitCase> updateExit(@PathVariable UUID id, @RequestBody ExitRequest request) { return ResponseEntity.ok(service.updateExit(id, request)); }
    @GetMapping("/assets") public List<AssetInventory> assets() { return service.assets(); }
    @PostMapping("/assets") public ResponseEntity<AssetInventory> createAsset(@RequestBody AssetRequest request) { return ResponseEntity.ok(service.createAsset(request)); }
    @PutMapping("/assets/{id}") public ResponseEntity<AssetInventory> updateAsset(@PathVariable UUID id, @RequestBody AssetRequest request) { return ResponseEntity.ok(service.updateAsset(id, request)); }
}
