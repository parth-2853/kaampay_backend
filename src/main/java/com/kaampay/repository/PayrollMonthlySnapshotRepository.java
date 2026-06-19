package com.kaampay.repository;

import com.kaampay.entity.PayrollMonthlySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayrollMonthlySnapshotRepository extends JpaRepository<PayrollMonthlySnapshot, String> {
    List<PayrollMonthlySnapshot> findByCompanyId(String companyId);
    List<PayrollMonthlySnapshot> findByEmployeeId(String employeeId);
    Optional<PayrollMonthlySnapshot> findByEmployeeIdAndMonthAndYear(String employeeId, Integer month, Integer year);
}