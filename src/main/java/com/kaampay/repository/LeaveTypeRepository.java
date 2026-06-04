package com.kaampay.repository;

import com.kaampay.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, String> {

    // Find leave types by company ID
    List<LeaveType> findByCompanyId(String companyId);

    // Find leave types by company ID ordered by leave name
    List<LeaveType> findByCompanyIdOrderByLeaveNameAsc(String companyId);

    // Find leave type by company ID and leave name
    Optional<LeaveType> findByCompanyIdAndLeaveName(String companyId, String leaveName);

    // Find leave types by company ID and is_paid status
    List<LeaveType> findByCompanyIdAndIsPaid(String companyId, String isPaid);

    // Find leave types by company ID and is_carry_forward status
    List<LeaveType> findByCompanyIdAndIsCarryForward(String companyId, String isCarryForward);

    // Find leave types by company ID and allocation type
    List<LeaveType> findByCompanyIdAndAllocationType(String companyId, String allocationType);

    // Find paid leaves only (is_paid = "true")
    @Query("SELECT l FROM LeaveType l WHERE l.companyId = :companyId AND l.isPaid = 'true'")
    List<LeaveType> findPaidLeavesByCompany(@Param("companyId") String companyId);

    // Find unpaid leaves only (is_paid = "false")
    @Query("SELECT l FROM LeaveType l WHERE l.companyId = :companyId AND l.isPaid = 'false'")
    List<LeaveType> findUnpaidLeavesByCompany(@Param("companyId") String companyId);

    // Find carry forward leaves only (is_carry_forward = "true")
    @Query("SELECT l FROM LeaveType l WHERE l.companyId = :companyId AND l.isCarryForward = 'true'")
    List<LeaveType> findCarryForwardLeavesByCompany(@Param("companyId") String companyId);

    // Find leaves with total days greater than specified value
    List<LeaveType> findByCompanyIdAndTotalDaysGreaterThan(String companyId, Integer days);

    // Find leaves with total days less than specified value
    List<LeaveType> findByCompanyIdAndTotalDaysLessThan(String companyId, Integer days);

    // Search leave types by name (partial match)
    List<LeaveType> findByCompanyIdAndLeaveNameContainingIgnoreCase(String companyId, String leaveName);

    // Get total paid leaves count for a company
    @Query("SELECT COUNT(l) FROM LeaveType l WHERE l.companyId = :companyId AND l.isPaid = 'true'")
    Long countPaidLeavesByCompany(@Param("companyId") String companyId);

    // Get total carry forward leaves count for a company
    @Query("SELECT COUNT(l) FROM LeaveType l WHERE l.companyId = :companyId AND l.isCarryForward = 'true'")
    Long countCarryForwardLeavesByCompany(@Param("companyId") String companyId);

    // Delete all leave types for a company
    void deleteByCompanyId(String companyId);

    // Check if leave type exists for a company
    boolean existsByCompanyIdAndLeaveName(String companyId, String leaveName);
}