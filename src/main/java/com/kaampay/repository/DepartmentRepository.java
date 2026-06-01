package com.kaampay.repository;

import com.kaampay.entity.Department;
import com.kaampay.entity.DepartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    List<Department> findByCompanyId(String companyId);

    List<Department> findByBranchId(String branchId);

    List<Department> findByStatus(DepartmentStatus status);

    List<Department> findByCompanyIdAndStatus(String companyId, DepartmentStatus status);

    // Prevents creating duplicate departments in the same company
    boolean existsByCompanyIdAndDepartmentNameIgnoreCase(String companyId, String departmentName);
}