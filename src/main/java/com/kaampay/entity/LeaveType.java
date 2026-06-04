package com.kaampay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaves")
public class LeaveType {

    @Id
    @Column(name = "leave_type_id", length = 36, nullable = false)
    private String leaveTypeId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "leave_name", length = 255, nullable = false)
    private String leaveName;

    @Column(name = "total_days")
    private Integer totalDays = 0;

    @Column(name = "is_paid", length = 50)
    private String isPaid = "true";

    @Column(name = "is_carry_forward", length = 50)
    private String isCarryForward = "false";

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "allocation_type", length = 50)
    private String allocationType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (leaveTypeId == null || leaveTypeId.isEmpty()) {
            leaveTypeId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (totalDays == null) totalDays = 0;
        if (isPaid == null || isPaid.isEmpty()) isPaid = "true";
        if (isCarryForward == null || isCarryForward.isEmpty()) isCarryForward = "false";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getIsCarryForward() {
        return isCarryForward;
    }

    public void setIsCarryForward(String isCarryForward) {
        this.isCarryForward = isCarryForward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}