package com.kaampay.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "company_name", length = 255, nullable = false)
    private String companyName;

    @Column(name = "business_type", length = 100)
    private String businessType;

    @Column(length = 100)
    private String state;

    @Column(name = "gst_number", length = 50)
    private String gstNumber;

    @Column(name = "phone_number")
    private Long phoneNumber;  // No unique constraint anymore

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "profile_image_base64", columnDefinition = "LONGTEXT")
    private String profileImageBase64;

    @Column(name = "signature_base64", columnDefinition = "LONGTEXT")
    private String signatureBase64;

    @Column(name = "referral_code", length = 50)
    private String referralCode;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "attendance_radius")
    private Integer attendanceRadius = 100;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('active','inactive','suspended') default 'active'")
    private CompanyStatus status = CompanyStatus.active;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", columnDefinition = "enum('trial','active','expired','cancelled') default 'trial'")
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.trial;

    @Column(name = "subscription_start_date")
    private LocalDateTime subscriptionStartDate;

    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Column(name = "current_plan", length = 100)
    private String currentPlan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (companyId == null || companyId.isEmpty()) {
            companyId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = CompanyStatus.active;
        if (subscriptionStatus == null) subscriptionStatus = SubscriptionStatus.trial;
        if (attendanceRadius == null) attendanceRadius = 100;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public Long getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(Long phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfileImageBase64() { return profileImageBase64; }
    public void setProfileImageBase64(String profileImageBase64) { this.profileImageBase64 = profileImageBase64; }

    public String getSignatureBase64() { return signatureBase64; }
    public void setSignatureBase64(String signatureBase64) { this.signatureBase64 = signatureBase64; }

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Integer getAttendanceRadius() { return attendanceRadius; }
    public void setAttendanceRadius(Integer attendanceRadius) { this.attendanceRadius = attendanceRadius; }

    public CompanyStatus getStatus() { return status; }
    public void setStatus(CompanyStatus status) { this.status = status; }

    public SubscriptionStatus getSubscriptionStatus() { return subscriptionStatus; }
    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) { this.subscriptionStatus = subscriptionStatus; }

    public LocalDateTime getSubscriptionStartDate() { return subscriptionStartDate; }
    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) { this.subscriptionStartDate = subscriptionStartDate; }

    public LocalDateTime getSubscriptionEndDate() { return subscriptionEndDate; }
    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) { this.subscriptionEndDate = subscriptionEndDate; }

    public String getCurrentPlan() { return currentPlan; }
    public void setCurrentPlan(String currentPlan) { this.currentPlan = currentPlan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}