package com.kaampay.repository;

import com.kaampay.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {
    List<LeaveRequest> findByCompanyId(String companyId);
    List<LeaveRequest> findByEmployeeId(String employeeId);
}