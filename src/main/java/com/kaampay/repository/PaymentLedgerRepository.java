package com.kaampay.repository;

import com.kaampay.entity.PaymentLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PaymentLedgerRepository extends JpaRepository<PaymentLedger, String> {
    List<PaymentLedger> findByCompanyId(String companyId);
    List<PaymentLedger> findByEmployeeId(String employeeId);
    List<PaymentLedger> findByEmployeeIdAndPaymentDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);
}