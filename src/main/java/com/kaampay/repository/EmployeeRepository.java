package com.kaampay.repository;

import com.kaampay.entity.Employee;
import com.kaampay.entity.EmployeeEnums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByPhone(String phone);

    List<Employee> findByCompanyId(String companyId);

    List<Employee> findByBranchId(String branchId);

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByDesignationId(String designationId);

    List<Employee> findByShiftId(String shiftId);

    // CHANGED: From Boolean to String
    List<Employee> findByIsActive(String isActive);

    // CHANGED: From Boolean to String
    List<Employee> findByCompanyIdAndIsActive(String companyId, String isActive);

    List<Employee> findByCompanyIdAndDepartmentId(String companyId, String departmentId);

    List<Employee> findByCompanyIdAndRole(String companyId, UserRole role);

    List<Employee> findByEmploymentType(String employmentType);

    List<Employee> findByBankName(String bankName);

    List<Employee> findByEmergencyPersonContact(String emergencyPersonContact);

    @Query("SELECT e FROM Employee e WHERE e.companyId = :companyId AND e.fullName LIKE %:name%")
    List<Employee> searchByName(@Param("companyId") String companyId, @Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.companyId = :companyId AND e.employmentType = :employmentType")
    List<Employee> findByCompanyIdAndEmploymentType(@Param("companyId") String companyId, @Param("employmentType") String employmentType);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);
}