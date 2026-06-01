package com.kaampay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "shift_fixed_breaks")
public class ShiftFixedBreak {

    @Id
    @Column(name = "break_id", length = 36, nullable = false)
    private String breakId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_day_id", nullable = false)
    @JsonBackReference("day-breaks")
    private ShiftDay shiftDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (breakId == null || breakId.isEmpty()) breakId = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBreakId() { return breakId; }
    public void setBreakId(String breakId) { this.breakId = breakId; }
    public ShiftDay getShiftDay() { return shiftDay; }
    public void setShiftDay(ShiftDay shiftDay) { this.shiftDay = shiftDay; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}