package com.kaampay.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

    @Id
    @Column(name = "plan_id", length = 36, nullable = false)
    private String planId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "plan_code", length = 50, unique = true)
    private String planCode;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(name = "currency", length = 3)
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_cycle", columnDefinition = "enum('monthly','yearly') default 'yearly'")
    private BillingCycle billingCycle = BillingCycle.yearly;

    @Column(name = "max_employees", nullable = false)
    private Integer maxEmployees;

    @Column(name = "max_branches", nullable = false)
    private Integer maxBranches;

    @Column(name = "max_storage_months")
    private Integer maxStorageMonths = 3;

    @Column(name = "max_shifts")
    private Integer maxShifts;

    @Column(name = "max_managers")
    private Integer maxManagers;

    @Column(name = "features", columnDefinition = "json")
    private String features;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (planId == null || planId.isEmpty()) {
            planId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
        if (displayOrder == null) displayOrder = 0;
        if (currency == null) currency = "INR";
        if (billingCycle == null) billingCycle = BillingCycle.yearly;
        if (maxStorageMonths == null) maxStorageMonths = 3;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getPlanCode() { return planCode; }
    public void setPlanCode(String planCode) { this.planCode = planCode; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BillingCycle getBillingCycle() { return billingCycle; }
    public void setBillingCycle(BillingCycle billingCycle) { this.billingCycle = billingCycle; }
    public Integer getMaxEmployees() { return maxEmployees; }
    public void setMaxEmployees(Integer maxEmployees) { this.maxEmployees = maxEmployees; }
    public Integer getMaxBranches() { return maxBranches; }
    public void setMaxBranches(Integer maxBranches) { this.maxBranches = maxBranches; }
    public Integer getMaxStorageMonths() { return maxStorageMonths; }
    public void setMaxStorageMonths(Integer maxStorageMonths) { this.maxStorageMonths = maxStorageMonths; }
    public Integer getMaxShifts() { return maxShifts; }
    public void setMaxShifts(Integer maxShifts) { this.maxShifts = maxShifts; }
    public Integer getMaxManagers() { return maxManagers; }
    public void setMaxManagers(Integer maxManagers) { this.maxManagers = maxManagers; }
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}