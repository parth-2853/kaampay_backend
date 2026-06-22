package com.kaampay.repository;

import com.kaampay.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, String> {
    Optional<SubscriptionPlan> findByPlanCode(String planCode);
    List<SubscriptionPlan> findByIsActiveOrderByDisplayOrderAsc(Boolean isActive);
}