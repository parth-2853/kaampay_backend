package com.kaampay.service;

import com.kaampay.entity.BillingCycle;
import com.kaampay.entity.SubscriptionPlan;
import com.kaampay.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository repository;

    public SubscriptionPlanService(SubscriptionPlanRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SubscriptionPlan createPlan(SubscriptionPlan plan) {
        return repository.save(plan);
    }

    public List<SubscriptionPlan> getAllPlans() {
        return repository.findAll();
    }

    public List<SubscriptionPlan> getActivePlans() {
        return repository.findByIsActiveOrderByDisplayOrderAsc(true);
    }

    public SubscriptionPlan getPlanById(String planId) {
        return repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Subscription Plan not found with id: " + planId));
    }

    public SubscriptionPlan getPlanByCode(String planCode) {
        return repository.findByPlanCode(planCode)
                .orElseThrow(() -> new RuntimeException("Subscription Plan not found with code: " + planCode));
    }

    @Transactional
    public SubscriptionPlan updatePlan(String planId, Map<String, Object> updates) {
        SubscriptionPlan existingPlan = getPlanById(planId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "planName": existingPlan.setPlanName((String) value); break;
                    case "planCode": existingPlan.setPlanCode((String) value); break;
                    case "price": existingPlan.setPrice(new BigDecimal(value.toString())); break;
                    case "originalPrice": existingPlan.setOriginalPrice(new BigDecimal(value.toString())); break;
                    case "currency": existingPlan.setCurrency((String) value); break;
                    case "billingCycle": existingPlan.setBillingCycle(BillingCycle.valueOf((String) value)); break;
                    case "maxEmployees": existingPlan.setMaxEmployees((Integer) value); break;
                    case "maxBranches": existingPlan.setMaxBranches((Integer) value); break;
                    case "maxStorageMonths": existingPlan.setMaxStorageMonths((Integer) value); break;
                    case "maxShifts": existingPlan.setMaxShifts((Integer) value); break;
                    case "maxManagers": existingPlan.setMaxManagers((Integer) value); break;
                    case "features": existingPlan.setFeatures((String) value); break;
                    case "isActive": existingPlan.setIsActive((Boolean) value); break;
                    case "displayOrder": existingPlan.setDisplayOrder((Integer) value); break;
                    default: break;
                }
            }
        });

        return repository.save(existingPlan);
    }

    @Transactional
    public void deletePlan(String planId) {
        SubscriptionPlan plan = getPlanById(planId);
        repository.delete(plan);
    }
}