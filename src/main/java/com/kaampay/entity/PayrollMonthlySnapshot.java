package com.kaampay.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll_monthly_snapshots")
public class PayrollMonthlySnapshot {

    @Id
    @Column(name = "snapshot_id", length = 36, nullable = false)
    private String snapshotId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "employee_id", length = 36, nullable = false)
    private String employeeId;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "payable_days")
    private Integer payableDays = 0;

    @Column(name = "base_salary", precision = 10, scale = 2)
    private BigDecimal baseSalary = BigDecimal.ZERO;

    @Column(name = "earned_salary", precision = 10, scale = 2)
    private BigDecimal earnedSalary = BigDecimal.ZERO;

    @Column(name = "total_dynamic_earnings", precision = 10, scale = 2)
    private BigDecimal totalDynamicEarnings = BigDecimal.ZERO;

    @Column(name = "total_dynamic_deductions", precision = 10, scale = 2)
    private BigDecimal totalDynamicDeductions = BigDecimal.ZERO;

    @Column(name = "total_overtime_pay", precision = 10, scale = 2)
    private BigDecimal totalOvertimePay = BigDecimal.ZERO;

    @Column(name = "net_payable", precision = 10, scale = 2)
    private BigDecimal netPayable = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PayrollStatus status = PayrollStatus.draft;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (snapshotId == null || snapshotId.isEmpty()) {
            snapshotId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = PayrollStatus.draft;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getSnapshotId() { return snapshotId; }
    public void setSnapshotId(String snapshotId) { this.snapshotId = snapshotId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getPayableDays() { return payableDays; }
    public void setPayableDays(Integer payableDays) { this.payableDays = payableDays; }

    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }

    public BigDecimal getEarnedSalary() { return earnedSalary; }
    public void setEarnedSalary(BigDecimal earnedSalary) { this.earnedSalary = earnedSalary; }

    public BigDecimal getTotalDynamicEarnings() { return totalDynamicEarnings; }
    public void setTotalDynamicEarnings(BigDecimal totalDynamicEarnings) { this.totalDynamicEarnings = totalDynamicEarnings; }

    public BigDecimal getTotalDynamicDeductions() { return totalDynamicDeductions; }
    public void setTotalDynamicDeductions(BigDecimal totalDynamicDeductions) { this.totalDynamicDeductions = totalDynamicDeductions; }

    public BigDecimal getTotalOvertimePay() { return totalOvertimePay; }
    public void setTotalOvertimePay(BigDecimal totalOvertimePay) { this.totalOvertimePay = totalOvertimePay; }

    public BigDecimal getNetPayable() { return netPayable; }
    public void setNetPayable(BigDecimal netPayable) { this.netPayable = netPayable; }

    public PayrollStatus getStatus() { return status; }
    public void setStatus(PayrollStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}