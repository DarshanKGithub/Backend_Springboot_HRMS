package com.hrms.tenancy;

import com.hrms.tenancy.entity.*;
import com.hrms.tenancy.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TenantDataAccessTest {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldSaveAndLoadTenantEntities() {
        Tenant tenant = new Tenant();
        tenant.setName("Demo Tenant");
        tenant.setDomain("demo.hrms");
        tenant.setActive(true);

        Tenant savedTenant = tenantRepository.save(tenant);

        Plan plan = new Plan();
        plan.setName("Monthly");
        plan.setPriceCents(99900);
        plan.setBillingPeriod("monthly");
        plan.setDescription("Monthly subscription");
        plan.setActive(true);

        Plan savedPlan = planRepository.save(plan);

        Subscription subscription = new Subscription();
        subscription.setTenantId(savedTenant.getId());
        subscription.setPlanId(savedPlan.getId());
        subscription.setStartsAt(LocalDate.now());
        subscription.setEndsAt(LocalDate.now().plusDays(30));
        subscription.setStatus("active");
        subscription.setPricePaidCents(99900);

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        assertThat(savedTenant.getId()).isNotNull();
        assertThat(tenantRepository.findById(savedTenant.getId())).isPresent();
        assertThat(savedPlan.getId()).isNotNull();
        assertThat(planRepository.findById(savedPlan.getId())).isPresent();
        assertThat(savedSubscription.getId()).isNotNull();
        assertThat(subscriptionRepository.findById(savedSubscription.getId())).isPresent();
    }
}
