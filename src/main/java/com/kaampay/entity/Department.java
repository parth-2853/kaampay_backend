package com.kaampay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "department_id", length = 36, nullable = false)
    private String departmentId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "branch_id", length = 36)
    private String branchId;

    @Column(name = "department_name", length = 255, nullable = false)
    private String departmentName;

    @Column(name = "department_head", length = 255)
    private String departmentHead;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "employee_count")
    private Integer employeeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('active','inactive') default 'active'")
    private DepartmentStatus status = DepartmentStatus.active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (departmentId == null || departmentId.isEmpty()) {
            departmentId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = DepartmentStatus.active;
        if (employeeCount == null) employeeCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDepartmentHead() { return departmentHead; }
    public void setDepartmentHead(String departmentHead) { this.departmentHead = departmentHead; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(Integer employeeCount) { this.employeeCount = employeeCount; }

    public DepartmentStatus getStatus() { return status; }
    public void setStatus(DepartmentStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}