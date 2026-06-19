package com.kaampay.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {

    @Id
    @Column(name = "attendance_id", length = 36, nullable = false)
    private String attendanceId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "employee_id", length = 36, nullable = false)
    private String employeeId;

    @Column(name = "branch_id", length = 36)
    private String branchId;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "punch_in_time")
    private LocalDateTime punchInTime;

    @Column(name = "punch_out_time")
    private LocalDateTime punchOutTime;

    @Column(name = "punch_in_image", columnDefinition = "TEXT")
    private String punchInImage;

    @Column(name = "punch_out_image", columnDefinition = "TEXT")
    private String punchOutImage;

    @Column(name = "punch_in_location", columnDefinition = "TEXT")
    private String punchInLocation;

    @Column(name = "punch_out_location", columnDefinition = "TEXT")
    private String punchOutLocation;

    @Column(name = "punch_in_latitude", precision = 10, scale = 8)
    private BigDecimal punchInLatitude;

    @Column(name = "punch_in_longitude", precision = 11, scale = 8)
    private BigDecimal punchInLongitude;

    @Column(name = "punch_out_latitude", precision = 10, scale = 8)
    private BigDecimal punchOutLatitude;

    @Column(name = "punch_out_longitude", precision = 11, scale = 8)
    private BigDecimal punchOutLongitude;

    @Column(name = "is_late")
    private Boolean isLate = false;

    @Column(name = "late_minutes")
    private Integer lateMinutes = 0;

    @Column(name = "total_work_hours", precision = 5, scale = 2)
    private BigDecimal totalWorkHours;

    @Column(name = "overtime_hours", precision = 5, scale = 2)
    private BigDecimal overtimeHours;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('present','absent','half_day','holiday','leave') default 'present'")
    private AttendanceStatus status = AttendanceStatus.present;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        if (attendanceId == null || attendanceId.isEmpty()) {
            attendanceId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isLate == null) isLate = false;
        if (lateMinutes == null) lateMinutes = 0;
        if (status == null) status = AttendanceStatus.present;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getAttendanceId() { return attendanceId; }
    public void setAttendanceId(String attendanceId) { this.attendanceId = attendanceId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public LocalDateTime getPunchInTime() { return punchInTime; }
    public void setPunchInTime(LocalDateTime punchInTime) { this.punchInTime = punchInTime; }

    public LocalDateTime getPunchOutTime() { return punchOutTime; }
    public void setPunchOutTime(LocalDateTime punchOutTime) { this.punchOutTime = punchOutTime; }

    public String getPunchInImage() { return punchInImage; }
    public void setPunchInImage(String punchInImage) { this.punchInImage = punchInImage; }

    public String getPunchOutImage() { return punchOutImage; }
    public void setPunchOutImage(String punchOutImage) { this.punchOutImage = punchOutImage; }

    public String getPunchInLocation() { return punchInLocation; }
    public void setPunchInLocation(String punchInLocation) { this.punchInLocation = punchInLocation; }

    public String getPunchOutLocation() { return punchOutLocation; }
    public void setPunchOutLocation(String punchOutLocation) { this.punchOutLocation = punchOutLocation; }

    public BigDecimal getPunchInLatitude() { return punchInLatitude; }
    public void setPunchInLatitude(BigDecimal punchInLatitude) { this.punchInLatitude = punchInLatitude; }

    public BigDecimal getPunchInLongitude() { return punchInLongitude; }
    public void setPunchInLongitude(BigDecimal punchInLongitude) { this.punchInLongitude = punchInLongitude; }

    public BigDecimal getPunchOutLatitude() { return punchOutLatitude; }
    public void setPunchOutLatitude(BigDecimal punchOutLatitude) { this.punchOutLatitude = punchOutLatitude; }

    public BigDecimal getPunchOutLongitude() { return punchOutLongitude; }
    public void setPunchOutLongitude(BigDecimal punchOutLongitude) { this.punchOutLongitude = punchOutLongitude; }

    public Boolean getIsLate() { return isLate; }
    public void setIsLate(Boolean isLate) { this.isLate = isLate; }

    public Integer getLateMinutes() { return lateMinutes; }
    public void setLateMinutes(Integer lateMinutes) { this.lateMinutes = lateMinutes; }

    public BigDecimal getTotalWorkHours() { return totalWorkHours; }
    public void setTotalWorkHours(BigDecimal totalWorkHours) { this.totalWorkHours = totalWorkHours; }

    public BigDecimal getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(BigDecimal overtimeHours) { this.overtimeHours = overtimeHours; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}