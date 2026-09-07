package com.hrms.tenancy.service;

import com.hrms.tenancy.dto.*;
import com.hrms.tenancy.dto.TenancyResponses.*;
import com.hrms.tenancy.entity.*;
import com.hrms.tenancy.repository.*;
import com.hrms.auth.entity.User;
import com.hrms.auth.repository.UserRepository;
import com.hrms.org.entity.AuditLog;
import com.hrms.org.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TenancyAdminService {
    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;
    private final PlanFeatureRepository planFeatureRepository;
    private final FeaturePackageRepository packageRepository;
    private final FeaturePackageFeatureRepository packageFeatureRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TenantFeatureRepository tenantFeatureRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final String paymentSecret;
    private final JdbcTemplate jdbcTemplate;
    private final String paymentKeyId;
    private final String paymentKeySecret;

    public TenancyAdminService(TenantRepository tenantRepository, PlanRepository planRepository, PlanFeatureRepository planFeatureRepository, FeaturePackageRepository packageRepository, FeaturePackageFeatureRepository packageFeatureRepository, SubscriptionRepository subscriptionRepository, TenantFeatureRepository tenantFeatureRepository, UserRepository userRepository, AuditLogRepository auditLogRepository, @Value("${app.razorpay.webhook-secret:}") String paymentSecret, JdbcTemplate jdbcTemplate, @Value("${app.razorpay.key-id:}") String paymentKeyId, @Value("${app.razorpay.key-secret:}") String paymentKeySecret) {
        this.tenantRepository = tenantRepository; this.planRepository = planRepository; this.planFeatureRepository = planFeatureRepository; this.packageRepository = packageRepository; this.packageFeatureRepository = packageFeatureRepository; this.subscriptionRepository = subscriptionRepository; this.tenantFeatureRepository = tenantFeatureRepository; this.userRepository = userRepository; this.auditLogRepository = auditLogRepository; this.paymentSecret = paymentSecret; this.jdbcTemplate = jdbcTemplate; this.paymentKeyId = paymentKeyId; this.paymentKeySecret = paymentKeySecret;
    }
    public TenantResponse createTenant(TenantRequest request) { Tenant item = new Tenant(); item.setName(request.name().trim()); item.setDomain(request.domain()); if (request.active()!=null) item.setActive(request.active()); return tenant(tenantRepository.save(item)); }
    public List<TenantResponse> listTenants() { return tenantRepository.findAll().stream().map(this::tenant).toList(); }
    public TenantResponse getTenant(UUID id) { return tenant(tenantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + id))); }
    public TenantResponse updateTenant(UUID id, TenantRequest request) { Tenant item = tenantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + id)); if (request.name()!=null) item.setName(request.name().trim()); if (request.domain()!=null) item.setDomain(request.domain()); if (request.active()!=null) item.setActive(request.active()); return tenant(item); }
    public PlanResponse createPlan(PlanRequest request) { Plan item = new Plan(); apply(item, request); Plan saved = planRepository.save(item); replacePlanFeatures(saved.getId(), request.featureKeys()); return plan(saved); }
    public List<PlanResponse> listPlans() { return planRepository.findAll().stream().map(this::plan).toList(); }
    public PlanResponse updatePlan(UUID id, PlanRequest request) { Plan item = planRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Plan not found: " + id)); apply(item, request); Plan saved = planRepository.save(item); if (request.featureKeys()!=null) replacePlanFeatures(id, request.featureKeys()); return plan(saved); }
    public PackageResponse createPackage(PackageRequest request) { FeaturePackage item = new FeaturePackage(); apply(item, request); FeaturePackage saved = packageRepository.save(item); replacePackageFeatures(saved.getId(), request.featureKeys()); return customPackage(saved); }
    public List<PackageResponse> listPackages() { return packageRepository.findAll().stream().map(this::customPackage).toList(); }
    public SubscriptionResponse createSubscription(UUID tenantId, SubscriptionRequest request) { if (!tenantRepository.existsById(tenantId)) throw new IllegalArgumentException("Tenant not found: " + tenantId); Plan plan = planRepository.findById(request.planId()).orElseThrow(() -> new IllegalArgumentException("Plan not found: " + request.planId())); Subscription item = new Subscription(); item.setTenantId(tenantId); item.setPlanId(plan.getId()); item.setStartsAt(request.startsAt()==null ? LocalDate.now() : request.startsAt()); item.setEndsAt(item.getStartsAt().plusMonths(request.durationMonths()==null ? 1 : request.durationMonths())); item.setStatus(request.status()==null ? "active" : request.status()); item.setPricePaidCents(plan.getPriceCents()); return subscription(subscriptionRepository.save(item)); }
    public List<SubscriptionResponse> subscriptions(UUID tenantId) { return subscriptionRepository.findByTenantId(tenantId).stream().map(this::subscription).toList(); }
    public FeatureResponse addFeature(UUID tenantId, FeatureRequest request) { TenantFeature item = new TenantFeature(); item.setTenantId(tenantId); item.setFeatureKey(request.featureKey().trim()); item.setEnabled(request.enabled()); return feature(tenantFeatureRepository.save(item)); }
    public List<FeatureResponse> features(UUID tenantId) { return tenantFeatureRepository.findByTenantId(tenantId).stream().map(this::feature).toList(); }
    public FeatureResponse updateFeature(UUID id, FeatureRequest request) { TenantFeature item = tenantFeatureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tenant feature not found: " + id)); item.setFeatureKey(request.featureKey().trim()); item.setEnabled(request.enabled()); return feature(tenantFeatureRepository.save(item)); }
    @Transactional
    public void deleteTenant(UUID id) { tenantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + id)); jdbcTemplate.update("DELETE FROM attendance_breaks WHERE attendance_id IN (SELECT id FROM attendance WHERE employee_id IN (SELECT id FROM employees WHERE tenant_id = ?))", id); String[] employeeTables = {"attendance", "timesheets", "overtime_requests", "attendance_regularizations", "comp_off_requests", "biometric_logs", "roster_entries", "employee_documents", "employee_grievances", "employee_trainings", "certifications", "feedback", "goals", "kpis", "performance_appraisals", "employee_salary_components", "salary_history", "salary_structures", "employee_loans", "reimbursements", "payslips"}; for (String table : employeeTables) jdbcTemplate.update("DELETE FROM " + table + " WHERE employee_id IN (SELECT id FROM employees WHERE tenant_id = ?)", id); jdbcTemplate.update("DELETE FROM employees WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM users WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM departments WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM designations WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM shifts WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM attendance_rules WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM leave_types WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM holidays WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM salary_components WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM tenant_features WHERE tenant_id = ?", id); jdbcTemplate.update("DELETE FROM subscriptions WHERE tenant_id = ?", id); tenantRepository.deleteById(id); }
    public void deletePlan(UUID id) { if (!planRepository.existsById(id)) throw new IllegalArgumentException("Plan not found: " + id); planFeatureRepository.deleteAll(planFeatureRepository.findByPlanId(id)); planRepository.deleteById(id); }
    public void deletePackage(UUID id) { if (!packageRepository.existsById(id)) throw new IllegalArgumentException("Package not found: " + id); packageFeatureRepository.deleteAll(packageFeatureRepository.findByPackageId(id)); packageRepository.deleteById(id); }
    public void cancelSubscription(UUID id) { Subscription item = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + id)); item.setStatus("cancelled"); subscriptionRepository.save(item); tenantFeatureRepository.findByTenantId(item.getTenantId()).stream().filter(feature -> feature.getFeatureKey().startsWith("subscription_")).forEach(feature -> feature.setEnabled(false)); }
    public void deleteFeature(UUID tenantId, UUID featureId) { TenantFeature item = tenantFeatureRepository.findById(featureId).filter(feature -> tenantId.equals(feature.getTenantId())).orElseThrow(() -> new IllegalArgumentException("Tenant feature not found: " + featureId)); tenantFeatureRepository.delete(item); }
    public List<FeatureResponse> batchFeatures(UUID tenantId, FeatureBatchRequest request) { request.featureKeys().forEach(key -> { TenantFeature item = tenantFeatureRepository.findByTenantId(tenantId).stream().filter(feature -> key.equals(feature.getFeatureKey())).findFirst().orElseGet(TenantFeature::new); item.setTenantId(tenantId); item.setFeatureKey(key.trim()); item.setEnabled(true); tenantFeatureRepository.save(item); }); return features(tenantId); }
    public UserAdminResponse updateUser(UUID id, AdminUserUpdateRequest request) { User item = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id)); if (request.role()!=null) item.setRole(request.role().trim()); if (request.tenantId()!=null && !tenantRepository.existsById(request.tenantId())) throw new IllegalArgumentException("Tenant not found: " + request.tenantId()); if (request.tenantId()!=null) item.setTenantId(request.tenantId()); return user(item); }
    public List<UserAdminResponse> users() { return userRepository.findAll().stream().map(this::user).toList(); }
    public List<AuditLogResponse> auditLogs(int page, int size, String objectType, UUID actorId) { return auditLogRepository.findAll().stream().filter(item -> objectType == null || item.getObjectType().equalsIgnoreCase(objectType)).filter(item -> actorId == null || actorId.equals(item.getActorId())).skip((long) Math.max(0, page - 1) * size).limit(size).map(item -> new AuditLogResponse(item.getId(), item.getActorId(), item.getAction(), item.getObjectType(), item.getObjectId(), item.getData(), item.getCreatedAt())).toList(); }
    public boolean verifyPayment(PaymentVerificationRequest request) { if (paymentSecret == null || paymentSecret.isBlank()) throw new IllegalStateException("Razorpay webhook secret is not configured"); try { Mac mac = Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(paymentSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256")); byte[] digest = mac.doFinal((request.orderId() + "|" + request.paymentId()).getBytes(StandardCharsets.UTF_8)); StringBuilder actual = new StringBuilder(); for (byte value : digest) actual.append(String.format("%02x", value)); return MessageDigest.isEqual(actual.toString().getBytes(StandardCharsets.UTF_8), request.signature().getBytes(StandardCharsets.UTF_8)); } catch (Exception exception) { throw new IllegalStateException("Unable to verify payment signature", exception); } }
    public PaymentOrderResponse createPaymentOrder(UUID tenantId, SubscriptionRequest request) { if (paymentKeyId == null || paymentKeyId.isBlank() || paymentKeySecret == null || paymentKeySecret.isBlank()) throw new IllegalStateException("Razorpay credentials are not configured"); SubscriptionResponse subscription = createSubscription(tenantId, request); Plan plan = planRepository.findById(subscription.planId()).orElseThrow(); var response = RestClient.builder().baseUrl("https://api.razorpay.com/v1").defaultHeaders(headers -> headers.setBasicAuth(paymentKeyId, paymentKeySecret)).build().post().uri("/orders").body(java.util.Map.of("amount", subscription.pricePaidCents(), "currency", "INR", "receipt", subscription.id().toString())).retrieve().body(java.util.Map.class); String orderId = response == null ? null : String.valueOf(response.get("id")); if (orderId == null || "null".equals(orderId)) throw new IllegalStateException("Razorpay did not return an order id"); return new PaymentOrderResponse(subscription.id(), orderId, paymentKeyId, subscription.pricePaidCents(), "INR"); }
    private UserAdminResponse user(User item) { return new UserAdminResponse(item.getId(), item.getFullName(), item.getEmail(), item.getRole(), item.getTenantId(), item.isActive()); }
    private TenantResponse tenant(Tenant i) { return new TenantResponse(i.getId(), i.getName(), i.getDomain(), i.isActive()); }
    private void apply(Plan i, PlanRequest r) { i.setName(r.name().trim()); i.setPriceCents(r.priceCents()); i.setBillingPeriod(r.billingPeriod()); i.setDescription(r.description()); if(r.active()!=null)i.setActive(r.active()); }
    private PlanResponse plan(Plan i) { return new PlanResponse(i.getId(), i.getName(), i.getPriceCents(), i.getBillingPeriod(), i.getDescription(), i.isActive(), planFeatureRepository.findByPlanId(i.getId()).stream().map(PlanFeature::getFeatureKey).toList()); }
    private void replacePlanFeatures(UUID id, List<String> keys) { if(keys==null)return; planFeatureRepository.deleteAll(planFeatureRepository.findByPlanId(id)); if(keys!=null)keys.stream().filter(k->!k.isBlank()).forEach(k->{PlanFeature f=new PlanFeature();f.setPlanId(id);f.setFeatureKey(k.trim());planFeatureRepository.save(f);}); }
    private void apply(FeaturePackage i, PackageRequest r) { i.setName(r.name().trim()); i.setDescription(r.description()); i.setPriceCents(r.priceCents()); }
    private PackageResponse customPackage(FeaturePackage i) { return new PackageResponse(i.getId(), i.getName(), i.getDescription(), i.getPriceCents(), packageFeatureRepository.findByPackageId(i.getId()).stream().map(FeaturePackageFeature::getFeatureKey).toList()); }
    private void replacePackageFeatures(UUID id, List<String> keys) { if(keys==null)return; packageFeatureRepository.deleteAll(packageFeatureRepository.findByPackageId(id)); keys.stream().filter(k->!k.isBlank()).forEach(k->{FeaturePackageFeature f=new FeaturePackageFeature();f.setPackageId(id);f.setFeatureKey(k.trim());packageFeatureRepository.save(f);}); }
    private SubscriptionResponse subscription(Subscription i) { return new SubscriptionResponse(i.getId(), i.getTenantId(), i.getPlanId(), i.getStartsAt(), i.getEndsAt(), i.getStatus(), i.getPricePaidCents()==null?0:i.getPricePaidCents()); }
    private FeatureResponse feature(TenantFeature i) { return new FeatureResponse(i.getId(), i.getTenantId(), i.getFeatureKey(), i.isEnabled()); }
}
