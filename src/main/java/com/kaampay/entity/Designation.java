package com.kaampay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "designations")
public class Designation {

    @Id
    @Column(name = "designation_id", length = 36, nullable = false)
    private String designationId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "department_id", length = 36)
    private String departmentId;

    @Column(name = "designation_name", length = 255, nullable = false)
    private String designationName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "employee_count")
    private Integer employeeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('active','inactive') default 'active'")
    private DesignationStatus status = DesignationStatus.active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (designationId == null || designationId.isEmpty()) {
            designationId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = DesignationStatus.active;
        if (employeeCount == null) employeeCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getDesignationId() { return designationId; }
    public void setDesignationId(String designationId) { this.designationId = designationId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDesignationName() { return designationName; }
    public void setDesignationName(String designationName) { this.designationName = designationName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(Integer employeeCount) { this.employeeCount = employeeCount; }

    public DesignationStatus getStatus() { return status; }
    public void setStatus(DesignationStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}