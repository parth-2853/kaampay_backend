package com.kaampay.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_branches")
public class CompanyBranch {

    @Id
    @Column(name = "branch_id", length = 36, nullable = false)
    private String branchId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "branch_name", length = 255, nullable = false)
    private String branchName;

    @Column(name = "branch_code", length = 50)
    private String branchCode;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String whatsapp;

    @Column(length = 255)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "attendance_radius")
    private Integer attendanceRadius = 100;

    @Column(name = "is_main_branch")
    private Boolean isMainBranch = false;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('active','inactive') default 'active'")
    private BranchStatus status = BranchStatus.active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "mac_address", length = 100)
    private String macAddress;

    @PrePersist
    protected void onCreate() {
        if (branchId == null || branchId.isEmpty()) {
            branchId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = BranchStatus.active;
        if (attendanceRadius == null) attendanceRadius = 100;
        if (isMainBranch == null) isMainBranch = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Integer getAttendanceRadius() { return attendanceRadius; }
    public void setAttendanceRadius(Integer attendanceRadius) { this.attendanceRadius = attendanceRadius; }

    public Boolean getIsMainBranch() { return isMainBranch; }
    public void setIsMainBranch(Boolean isMainBranch) { this.isMainBranch = isMainBranch; }

    public BranchStatus getStatus() { return status; }
    public void setStatus(BranchStatus status) { this.status = status; }

    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}