package com.kaampay.repository;

import com.kaampay.entity.Company;
import com.kaampay.entity.CompanyStatus;
import com.kaampay.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByGstNumber(String gstNumber);

    Optional<Company> findByReferralCode(String referralCode);

    // Changed from Optional<Company> to List<Company> - returns multiple companies
    List<Company> findByPhoneNumber(Long phoneNumber);

    List<Company> findByStatus(CompanyStatus status);

    List<Company> findBySubscriptionStatus(SubscriptionStatus subscriptionStatus);

    List<Company> findByState(String state);

    List<Company> findByBusinessType(String businessType);

    @Query("SELECT c FROM Company c WHERE c.subscriptionEndDate < :now AND c.subscriptionStatus = 'active'")
    List<Company> findExpiredSubscriptions(@Param("now") LocalDateTime now);

    @Query("SELECT c FROM Company c WHERE c.subscriptionEndDate BETWEEN :start AND :end")
    List<Company> findSubscriptionsExpiringBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    boolean existsByGstNumber(String gstNumber);

    boolean existsByCompanyName(String companyName);
}