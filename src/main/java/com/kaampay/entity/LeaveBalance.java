package com.kaampay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {

    @Id
    @Column(name = "balance_id", length = 36, nullable = false)
    private String balanceId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "employee_id", length = 36, nullable = false)
    private String employeeId;

    @Column(name = "leave_type_id", length = 36, nullable = false)
    private String leaveTypeId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "total_days")
    private Integer totalDays = 0;

    @Column(name = "used_days")
    private Integer usedDays = 0;

    @Column(name = "remaining_days")
    private Integer remainingDays = 0;

    @Column(name = "carried_over")
    private Integer carriedOver = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (balanceId == null || balanceId.isEmpty()) {
            balanceId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBalanceId() { return balanceId; }
    public void setBalanceId(String balanceId) { this.balanceId = balanceId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLeaveTypeId() { return leaveTypeId; }
    public void setLeaveTypeId(String leaveTypeId) { this.leaveTypeId = leaveTypeId; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getTotalDays() { return totalDays; }
    public void setTotalDays(Integer totalDays) { this.totalDays = totalDays; }

    public Integer getUsedDays() { return usedDays; }
    public void setUsedDays(Integer usedDays) { this.usedDays = usedDays; }

    public Integer getRemainingDays() { return remainingDays; }
    public void setRemainingDays(Integer remainingDays) { this.remainingDays = remainingDays; }

    public Integer getCarriedOver() { return carriedOver; }
    public void setCarriedOver(Integer carriedOver) { this.carriedOver = carriedOver; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}