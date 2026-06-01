package com.kaampay.repository;

import com.kaampay.entity.Designation;
import com.kaampay.entity.DesignationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignationRepository extends JpaRepository<Designation, String> {

    List<Designation> findByCompanyId(String companyId);

    List<Designation> findByDepartmentId(String departmentId);

    List<Designation> findByStatus(DesignationStatus status);

    // Prevents creating duplicate designations in the same company
    boolean existsByCompanyIdAndDesignationNameIgnoreCase(String companyId, String designationName);
}