package com.kaampay.service;

import com.kaampay.entity.CompanySubscription;
import com.kaampay.entity.SubscriptionStatus;
import com.kaampay.repository.CompanySubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CompanySubscriptionService {

    private final CompanySubscriptionRepository repository;

    public CompanySubscriptionService(CompanySubscriptionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CompanySubscription createSubscription(CompanySubscription subscription) {
        return repository.save(subscription);
    }

    public List<CompanySubscription> getAllSubscriptions() {
        return repository.findAll();
    }

    public CompanySubscription getSubscriptionById(String subscriptionId) {
        return repository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));
    }

    public List<CompanySubscription> getSubscriptionsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    public List<CompanySubscription> getSubscriptionsByPlanId(String planId) {
        return repository.findByPlanId(planId);
    }

    public List<CompanySubscription> getSubscriptionsByStatus(SubscriptionStatus status) {
        return repository.findByStatus(status);
    }

    @Transactional
    public CompanySubscription updateSubscription(String subscriptionId, Map<String, Object> updates) {
        CompanySubscription existingSub = getSubscriptionById(subscriptionId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "companyId": existingSub.setCompanyId((String) value); break;
                    case "planId": existingSub.setPlanId((String) value); break;
                    case "startDate": existingSub.setStartDate(LocalDateTime.parse((String) value)); break;
                    case "endDate": existingSub.setEndDate(LocalDateTime.parse((String) value)); break;
                    case "status": existingSub.setStatus(SubscriptionStatus.valueOf((String) value)); break;
                    case "autoRenew": existingSub.setAutoRenew((Boolean) value); break;
                    case "paymentId": existingSub.setPaymentId((String) value); break;
                    case "amountPaid": existingSub.setAmountPaid(new BigDecimal(value.toString())); break;
                    default: break;
                }
            }
        });

        return repository.save(existingSub);
    }

    @Transactional
    public void deleteSubscription(String subscriptionId) {
        CompanySubscription subscription = getSubscriptionById(subscriptionId);
        repository.delete(subscription);
    }
}