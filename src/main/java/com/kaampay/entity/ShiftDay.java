package com.kaampay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shift_days")
public class ShiftDay {

    @Id
    @Column(name = "shift_day_id", length = 36, nullable = false)
    private String shiftDayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id", nullable = false)
    @JsonBackReference("shift-days")
    private Shift shift;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_name", nullable = false)
    private DayOfWeekName dayName;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "has_break")
    private Boolean hasBreak = false;

    @Column(name = "is_flexible_break")
    private Boolean isFlexibleBreak = false;

    @Column(name = "break_duration_minutes")
    private Integer breakDurationMinutes = 0;

    @Column(name = "has_occasional_days")
    private Boolean hasOccasionalDays = false;

    private Boolean week1 = false;
    private Boolean week2 = false;
    private Boolean week3 = false;
    private Boolean week4 = false;
    private Boolean week5 = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shiftDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("day-breaks")
    private List<ShiftFixedBreak> fixedBreaks = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (shiftDayId == null || shiftDayId.isEmpty()) shiftDayId = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public void addFixedBreak(ShiftFixedBreak breakItem) {
        fixedBreaks.add(breakItem);
        breakItem.setShiftDay(this);
    }

    // Getters and Setters
    public String getShiftDayId() { return shiftDayId; }
    public void setShiftDayId(String shiftDayId) { this.shiftDayId = shiftDayId; }
    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }
    public DayOfWeekName getDayName() { return dayName; }
    public void setDayName(DayOfWeekName dayName) { this.dayName = dayName; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Boolean getHasBreak() { return hasBreak; }
    public void setHasBreak(Boolean hasBreak) { this.hasBreak = hasBreak; }
    public Boolean getIsFlexibleBreak() { return isFlexibleBreak; }
    public void setIsFlexibleBreak(Boolean flexibleBreak) { isFlexibleBreak = flexibleBreak; }
    public Integer getBreakDurationMinutes() { return breakDurationMinutes; }
    public void setBreakDurationMinutes(Integer breakDurationMinutes) { this.breakDurationMinutes = breakDurationMinutes; }
    public Boolean getHasOccasionalDays() { return hasOccasionalDays; }
    public void setHasOccasionalDays(Boolean hasOccasionalDays) { this.hasOccasionalDays = hasOccasionalDays; }
    public Boolean getWeek1() { return week1; }
    public void setWeek1(Boolean week1) { this.week1 = week1; }
    public Boolean getWeek2() { return week2; }
    public void setWeek2(Boolean week2) { this.week2 = week2; }
    public Boolean getWeek3() { return week3; }
    public void setWeek3(Boolean week3) { this.week3 = week3; }
    public Boolean getWeek4() { return week4; }
    public void setWeek4(Boolean week4) { this.week4 = week4; }
    public Boolean getWeek5() { return week5; }
    public void setWeek5(Boolean week5) { this.week5 = week5; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<ShiftFixedBreak> getFixedBreaks() { return fixedBreaks; }
    public void setFixedBreaks(List<ShiftFixedBreak> fixedBreaks) {
        this.fixedBreaks.clear();
        if (fixedBreaks != null) {
            fixedBreaks.forEach(this::addFixedBreak);
        }
    }
}