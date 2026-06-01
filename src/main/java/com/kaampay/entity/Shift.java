package com.kaampay.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @Column(name = "shift_id", length = 36, nullable = false)
    private String shiftId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "branch_id", length = 36)
    private String branchId;

    @Column(name = "shift_name", length = 255, nullable = false)
    private String shiftName;

    @Column(name = "late_allowance_minutes")
    private Integer lateAllowanceMinutes = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('active','inactive') default 'active'")
    private ShiftStatus status = ShiftStatus.active;

    @Column(name = "created_by", length = 36)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("shift-days")
    private List<ShiftDay> shiftDays = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (shiftId == null || shiftId.isEmpty()) shiftId = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = ShiftStatus.active;
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public void addShiftDay(ShiftDay day) {
        shiftDays.add(day);
        day.setShift(this);
    }

    // Getters and Setters
    public String getShiftId() { return shiftId; }
    public void setShiftId(String shiftId) { this.shiftId = shiftId; }
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }
    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }
    public Integer getLateAllowanceMinutes() { return lateAllowanceMinutes; }
    public void setLateAllowanceMinutes(Integer lateAllowanceMinutes) { this.lateAllowanceMinutes = lateAllowanceMinutes; }
    public ShiftStatus getStatus() { return status; }
    public void setStatus(ShiftStatus status) { this.status = status; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<ShiftDay> getShiftDays() { return shiftDays; }
    public void setShiftDays(List<ShiftDay> shiftDays) {
        this.shiftDays.clear();
        if (shiftDays != null) {
            shiftDays.forEach(this::addShiftDay);
        }
    }
}