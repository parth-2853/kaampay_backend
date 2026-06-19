package com.kaampay.repository;

import com.kaampay.entity.EmployeeTransaction;
import com.kaampay.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeTransactionRepository extends JpaRepository<EmployeeTransaction, String> {
    List<EmployeeTransaction> findByCompanyId(String companyId);
    List<EmployeeTransaction> findByEmployeeId(String employeeId);
    List<EmployeeTransaction> findByEmployeeIdAndTransactionType(String employeeId, TransactionType type);
    List<EmployeeTransaction> findByEmployeeIdAndTransactionDateBetween(String employeeId, LocalDate startDate, LocalDate endDate);
}