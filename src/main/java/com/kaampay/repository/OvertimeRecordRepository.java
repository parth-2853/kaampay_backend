package com.kaampay.repository;

import com.kaampay.entity.OvertimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, String> {
    List<OvertimeRecord> findByCompanyId(String companyId);
    List<OvertimeRecord> findByEmployeeId(String employeeId);
    List<OvertimeRecord> findByEmployeeIdAndOvertimeDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);
}