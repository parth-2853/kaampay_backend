package com.kaampay.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "holidays")
public class Holiday {

    @Id
    @Column(name = "holiday_id", length = 36, nullable = false)
    private String holidayId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "holiday_name", length = 255, nullable = false)
    private String holidayName;

    @Column(name = "holiday_start_date", nullable = false)
    private LocalDate holidayStartDate;

    @Column(name = "holiday_end_date", nullable = false)
    private LocalDate holidayEndDate;

    @Column(name = "is_event", length = 50)
    private String isEvent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (holidayId == null || holidayId.isEmpty()) {
            holidayId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isEvent == null || isEvent.isEmpty()) {
            isEvent = "false";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public LocalDate getHolidayStartDate() {
        return holidayStartDate;
    }

    public void setHolidayStartDate(LocalDate holidayStartDate) {
        this.holidayStartDate = holidayStartDate;
    }

    public LocalDate getHolidayEndDate() {
        return holidayEndDate;
    }

    public void setHolidayEndDate(LocalDate holidayEndDate) {
        this.holidayEndDate = holidayEndDate;
    }

    public String getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(String isEvent) {
        this.isEvent = isEvent;
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