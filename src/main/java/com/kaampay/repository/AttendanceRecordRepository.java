package com.kaampay.repository;

import com.kaampay.entity.AttendanceRecord;
import com.kaampay.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, String> {

    List<AttendanceRecord> findByCompanyId(String companyId);

    List<AttendanceRecord> findByEmployeeId(String employeeId);

    List<AttendanceRecord> findByBranchId(String branchId);

    List<AttendanceRecord> findByCompanyIdAndAttendanceDate(String companyId, LocalDate attendanceDate);

    List<AttendanceRecord> findByEmployeeIdAndAttendanceDate(String employeeId, LocalDate attendanceDate);

    Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDateAndCompanyId(String employeeId, LocalDate attendanceDate, String companyId);

    List<AttendanceRecord> findByStatus(AttendanceStatus status);

    // Date Range Queries
    List<AttendanceRecord> findByCompanyIdAndAttendanceDateBetween(String companyId, LocalDate startDate, LocalDate endDate);

    List<AttendanceRecord> findByEmployeeIdAndAttendanceDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);
}