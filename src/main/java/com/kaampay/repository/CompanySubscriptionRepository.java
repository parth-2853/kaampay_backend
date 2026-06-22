package com.kaampay.repository;

import com.kaampay.entity.CompanySubscription;
import com.kaampay.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanySubscriptionRepository extends JpaRepository<CompanySubscription, String> {
    List<CompanySubscription> findByCompanyId(String companyId);
    List<CompanySubscription> findByPlanId(String planId);
    List<CompanySubscription> findByStatus(SubscriptionStatus status);
}