package com.kaampay.repository;

import com.kaampay.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, String> {
    List<LeaveBalance> findByCompanyId(String companyId);
    List<LeaveBalance> findByEmployeeId(String employeeId);
    List<LeaveBalance> findByEmployeeIdAndYear(String employeeId, Integer year);
    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(String employeeId, String leaveTypeId, Integer year);
}