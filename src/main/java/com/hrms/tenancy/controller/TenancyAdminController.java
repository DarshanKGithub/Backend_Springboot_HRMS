package com.hrms.tenancy.controller;

import com.hrms.tenancy.dto.*;
import com.hrms.tenancy.dto.TenancyResponses.*;
import com.hrms.tenancy.service.TenancyAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class TenancyAdminController {
    private final TenancyAdminService service;
    public TenancyAdminController(TenancyAdminService service) { this.service = service; }
    @PostMapping("/tenants") public ResponseEntity<TenantResponse> createTenant(@RequestBody TenantRequest request) { return ResponseEntity.ok(service.createTenant(request)); }
    @GetMapping("/tenants") public List<TenantResponse> listTenants() { return service.listTenants(); }
    @GetMapping("/tenants/{id}") public ResponseEntity<TenantResponse> getTenant(@PathVariable UUID id) { return ResponseEntity.ok(service.getTenant(id)); }
    @PatchMapping("/tenants/{id}") public ResponseEntity<TenantResponse> updateTenant(@PathVariable UUID id, @RequestBody TenantRequest request) { return ResponseEntity.ok(service.updateTenant(id, request)); }
    @DeleteMapping("/tenants/{id}") public ResponseEntity<Void> deleteTenant(@PathVariable UUID id) { service.deleteTenant(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/plans") public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest request) { return ResponseEntity.ok(service.createPlan(request)); }
    @GetMapping("/plans") public List<PlanResponse> listPlans() { return service.listPlans(); }
    @PatchMapping("/plans/{id}") public ResponseEntity<PlanResponse> updatePlan(@PathVariable UUID id, @RequestBody PlanRequest request) { return ResponseEntity.ok(service.updatePlan(id, request)); }
    @DeleteMapping("/plans/{id}") public ResponseEntity<Void> deletePlan(@PathVariable UUID id) { service.deletePlan(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/packages") public ResponseEntity<PackageResponse> createPackage(@RequestBody PackageRequest request) { return ResponseEntity.ok(service.createPackage(request)); }
    @GetMapping("/packages") public List<PackageResponse> listPackages() { return service.listPackages(); }
    @DeleteMapping("/packages/{id}") public ResponseEntity<Void> deletePackage(@PathVariable UUID id) { service.deletePackage(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/tenants/{tenantId}/subscriptions") public ResponseEntity<SubscriptionResponse> createSubscription(@PathVariable UUID tenantId, @RequestBody SubscriptionRequest request) { return ResponseEntity.ok(service.createSubscription(tenantId, request)); }
    @GetMapping("/tenants/{tenantId}/subscriptions") public List<SubscriptionResponse> subscriptions(@PathVariable UUID tenantId) { return service.subscriptions(tenantId); }
    @DeleteMapping("/subscriptions/{id}") public ResponseEntity<Void> cancelSubscription(@PathVariable UUID id) { service.cancelSubscription(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/tenants/{tenantId}/features") public ResponseEntity<FeatureResponse> addFeature(@PathVariable UUID tenantId, @RequestBody FeatureRequest request) { return ResponseEntity.ok(service.addFeature(tenantId, request)); }
    @GetMapping("/tenants/{tenantId}/features") public List<FeatureResponse> features(@PathVariable UUID tenantId) { return service.features(tenantId); }
    @PatchMapping("/tenants/{tenantId}/features/{featureId}") public ResponseEntity<FeatureResponse> updateFeature(@PathVariable UUID featureId, @RequestBody FeatureRequest request) { return ResponseEntity.ok(service.updateFeature(featureId, request)); }
    @DeleteMapping("/tenants/{tenantId}/features/{featureId}") public ResponseEntity<Void> deleteFeature(@PathVariable UUID tenantId, @PathVariable UUID featureId) { service.deleteFeature(tenantId, featureId); return ResponseEntity.noContent().build(); }
    @PostMapping("/tenants/{tenantId}/features/batch") public List<FeatureResponse> batchFeatures(@PathVariable UUID tenantId, @RequestBody FeatureBatchRequest request) { return service.batchFeatures(tenantId, request); }
    @PatchMapping("/users/{id}") public ResponseEntity<UserAdminResponse> updateUser(@PathVariable UUID id, @RequestBody AdminUserUpdateRequest request) { return ResponseEntity.ok(service.updateUser(id, request)); }
    @GetMapping("/users") public List<UserAdminResponse> users() { return service.users(); }
    @GetMapping("/audit-logs") public List<AuditLogResponse> auditLogs(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) String objectType, @RequestParam(required = false) UUID actorId) { return service.auditLogs(page, size, objectType, actorId); }
    @PostMapping("/subscriptions/verify-payment") public ResponseEntity<Void> verifyPayment(@RequestBody PaymentVerificationRequest request) { if (!service.verifyPayment(request)) return ResponseEntity.badRequest().build(); return ResponseEntity.ok().build(); }
    @PostMapping("/tenants/{tenantId}/subscriptions/order") public ResponseEntity<PaymentOrderResponse> createPaymentOrder(@PathVariable UUID tenantId, @RequestBody SubscriptionRequest request) { return ResponseEntity.ok(service.createPaymentOrder(tenantId, request)); }
}
