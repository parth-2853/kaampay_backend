package com.kaampay.service;

import com.kaampay.entity.Employee;
import com.kaampay.entity.EmployeeEnums.*;
import com.kaampay.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }

        // Auto-generate employee code if not provided
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().isEmpty()) {
            employee.setEmployeeCode(generateEmployeeCode());
        } else if (repository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists: " + employee.getEmployeeCode());
        }

        if (employee.getEmail() != null && repository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email already exists: " + employee.getEmail());
        }

        return repository.save(employee);
    }

    private String generateEmployeeCode() {
        String prefix = "EMP";
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefix + randomPart;
    }

    // READ
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(String employeeId) {
        return repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
    }

    public Employee getEmployeeByCode(String employeeCode) {
        return repository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new RuntimeException("Employee not found with code: " + employeeCode));
    }

    public List<Employee> getEmployeesByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    public List<Employee> getEmployeesByBranchId(String branchId) {
        return repository.findByBranchId(branchId);
    }

    public List<Employee> getEmployeesByDepartmentId(String departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByDesignationId(String designationId) {
        return repository.findByDesignationId(designationId);
    }

    public List<Employee> getEmployeesByShiftId(String shiftId) {
        return repository.findByShiftId(shiftId);
    }

    public List<Employee> getActiveEmployees() {
        return repository.findByIsActive("true");
    }

    public List<Employee> getInactiveEmployees() {
        return repository.findByIsActive("false");
    }

    public List<Employee> getEmployeesByCompanyAndDepartment(String companyId, String departmentId) {
        return repository.findByCompanyIdAndDepartmentId(companyId, departmentId);
    }

    public List<Employee> getEmployeesByCompanyAndRole(String companyId, UserRole role) {
        return repository.findByCompanyIdAndRole(companyId, role);
    }

    // New methods for new columns
    public List<Employee> getEmployeesByEmploymentType(String employmentType) {
        return repository.findByEmploymentType(employmentType);
    }

    public List<Employee> getEmployeesByCompanyAndEmploymentType(String companyId, String employmentType) {
        return repository.findByCompanyIdAndEmploymentType(companyId, employmentType);
    }

    public List<Employee> searchEmployeesByName(String companyId, String name) {
        return repository.searchByName(companyId, name);
    }

    public List<Employee> getEmployeesByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    public List<Employee> getEmployeesByEmergencyContact(String emergencyContact) {
        return repository.findByEmergencyPersonContact(emergencyContact);
    }

    // UPDATE - Partial update (UPDATED with all columns including isActive)
    @Transactional
    public Employee updateEmployee(String employeeId, Map<String, Object> updates) {
        Employee existingEmployee = getEmployeeById(employeeId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "branchId":
                        existingEmployee.setBranchId((String) value);
                        break;
                    case "departmentId":
                        existingEmployee.setDepartmentId((String) value);
                        break;
                    case "designationId":
                        existingEmployee.setDesignationId((String) value);
                        break;
                    case "shiftId":
                        existingEmployee.setShiftId((String) value);
                        break;
                    case "employeeCode":
                        String newCode = (String) value;
                        if (!newCode.equals(existingEmployee.getEmployeeCode()) &&
                                repository.existsByEmployeeCode(newCode)) {
                            throw new RuntimeException("Employee code already exists: " + newCode);
                        }
                        existingEmployee.setEmployeeCode(newCode);
                        break;
                    case "fullName":
                        existingEmployee.setFullName((String) value);
                        break;
                    case "email":
                        String newEmail = (String) value;
                        if (newEmail != null && !newEmail.equals(existingEmployee.getEmail()) &&
                                repository.existsByEmail(newEmail)) {
                            throw new RuntimeException("Email already exists: " + newEmail);
                        }
                        existingEmployee.setEmail(newEmail);
                        break;
                    case "phone":
                        existingEmployee.setPhone((String) value);
                        break;
                    case "alternatePhone":
                        existingEmployee.setAlternatePhone((String) value);
                        break;
                    case "gender":
                        existingEmployee.setGender(Gender.valueOf((String) value));
                        break;
                    case "dateOfBirth":
                        existingEmployee.setDateOfBirth(LocalDate.parse((String) value));
                        break;
                    case "dateOfJoining":
                        existingEmployee.setDateOfJoining(LocalDate.parse((String) value));
                        break;
                    case "bloodGroup":
                        existingEmployee.setBloodGroup((String) value);
                        break;
                    case "address":
                        existingEmployee.setAddress((String) value);
                        break;
                    case "profileImageBase64":
                        existingEmployee.setProfileImageBase64((String) value);
                        break;
                    case "salaryType":
                        existingEmployee.setSalaryType(SalaryType.valueOf((String) value));
                        break;
                    case "salaryAmount":
                        existingEmployee.setSalaryAmount(new BigDecimal(value.toString()));
                        break;
                    case "role":
                        existingEmployee.setRole(UserRole.valueOf((String) value));
                        break;
                    case "punchInType":
                        existingEmployee.setPunchInType((String) value);
                        break;
                    case "attendancePermission":
                        existingEmployee.setAttendancePermission((String) value);
                        break;
                    case "attendanceLocation":
                        existingEmployee.setAttendanceLocation((String) value);
                        break;
                    case "isActive":
                        existingEmployee.setIsActive((String) value);
                        break;
                    case "createdBy":
                        existingEmployee.setCreatedBy((String) value);
                        break;
                    // NEW COLUMNS
                    case "employmentType":
                        existingEmployee.setEmploymentType((String) value);
                        break;
                    case "bankName":
                        existingEmployee.setBankName((String) value);
                        break;
                    case "bankBranch":
                        existingEmployee.setBankBranch((String) value);
                        break;
                    case "bankAccountNumber":
                        existingEmployee.setBankAccountNumber((String) value);
                        break;
                    case "bankIfsc":
                        existingEmployee.setBankIfsc((String) value);
                        break;
                    case "upiIdDetail":
                        existingEmployee.setUpiIdDetail((String) value);
                        break;
                    case "aadharCard":
                        existingEmployee.setAadharCard((String) value);
                        break;
                    case "panCard":
                        existingEmployee.setPanCard((String) value);
                        break;
                    case "drivingLicense":
                        existingEmployee.setDrivingLicense((String) value);
                        break;
                    case "emergencyPersonName":
                        existingEmployee.setEmergencyPersonName((String) value);
                        break;
                    case "emergencyPersonContact":
                        existingEmployee.setEmergencyPersonContact((String) value);
                        break;
                    case "emergencyPersonRelation":
                        existingEmployee.setEmergencyPersonRelation((String) value);
                        break;
                    case "emergencyPersonAddress":
                        existingEmployee.setEmergencyPersonAddress((String) value);
                        break;
                    case "referenceName":
                        existingEmployee.setReferenceName((String) value);
                        break;
                    case "referenceMobileNo":
                        existingEmployee.setReferenceMobileNo((String) value);
                        break;
                    default:
                        break;
                }
            }
        });

        return repository.save(existingEmployee);
    }

    // SOFT DELETE (set isActive = "false")
    @Transactional
    public Employee softDeleteEmployee(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setIsActive("false");
        return repository.save(employee);
    }

    // HARD DELETE
    @Transactional
    public void hardDeleteEmployee(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        repository.delete(employee);
    }

    // RESTORE (set isActive = "true")
    @Transactional
    public Employee restoreEmployee(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setIsActive("true");
        return repository.save(employee);
    }
}