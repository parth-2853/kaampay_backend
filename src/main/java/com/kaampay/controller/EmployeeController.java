package com.kaampay.controller;

import com.kaampay.entity.Employee;
import com.kaampay.entity.EmployeeEnums.UserRole;
import com.kaampay.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = service.createEmployee(employee);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getEmployeeById(employeeId));
    }

    @GetMapping("/getByCode/{employeeCode}")
    public ResponseEntity<Employee> getEmployeeByCode(@PathVariable String employeeCode) {
        return ResponseEntity.ok(service.getEmployeeByCode(employeeCode));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getEmployeesByCompanyId(companyId));
    }

    @GetMapping("/getByBranch/{branchId}")
    public ResponseEntity<List<Employee>> getEmployeesByBranchId(@PathVariable String branchId) {
        return ResponseEntity.ok(service.getEmployeesByBranchId(branchId));
    }

    @GetMapping("/getByDepartment/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable String departmentId) {
        return ResponseEntity.ok(service.getEmployeesByDepartmentId(departmentId));
    }

    @GetMapping("/getByDesignation/{designationId}")
    public ResponseEntity<List<Employee>> getEmployeesByDesignationId(@PathVariable String designationId) {
        return ResponseEntity.ok(service.getEmployeesByDesignationId(designationId));
    }

    @GetMapping("/getByShift/{shiftId}")
    public ResponseEntity<List<Employee>> getEmployeesByShiftId(@PathVariable String shiftId) {
        return ResponseEntity.ok(service.getEmployeesByShiftId(shiftId));
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<Employee>> getActiveEmployees() {
        return ResponseEntity.ok(service.getActiveEmployees());
    }

    @GetMapping("/getInactive")
    public ResponseEntity<List<Employee>> getInactiveEmployees() {
        return ResponseEntity.ok(service.getInactiveEmployees());
    }

    @GetMapping("/getByCompanyAndDepartment")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyAndDepartment(
            @RequestParam String companyId,
            @RequestParam String departmentId) {
        return ResponseEntity.ok(service.getEmployeesByCompanyAndDepartment(companyId, departmentId));
    }

    @GetMapping("/getByCompanyAndRole")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyAndRole(
            @RequestParam String companyId,
            @RequestParam UserRole role) {
        return ResponseEntity.ok(service.getEmployeesByCompanyAndRole(companyId, role));
    }

    // NEW ENDPOINTS for new columns
    @GetMapping("/getByEmploymentType/{employmentType}")
    public ResponseEntity<List<Employee>> getEmployeesByEmploymentType(@PathVariable String employmentType) {
        return ResponseEntity.ok(service.getEmployeesByEmploymentType(employmentType));
    }

    @GetMapping("/getByCompanyAndEmploymentType")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyAndEmploymentType(
            @RequestParam String companyId,
            @RequestParam String employmentType) {
        return ResponseEntity.ok(service.getEmployeesByCompanyAndEmploymentType(companyId, employmentType));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String companyId,
            @RequestParam String name) {
        return ResponseEntity.ok(service.searchEmployeesByName(companyId, name));
    }

    @GetMapping("/getByPhone/{phone}")
    public ResponseEntity<List<Employee>> getEmployeesByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(service.getEmployeesByPhone(phone));
    }

    @GetMapping("/getByEmergencyContact/{emergencyContact}")
    public ResponseEntity<List<Employee>> getEmployeesByEmergencyContact(@PathVariable String emergencyContact) {
        return ResponseEntity.ok(service.getEmployeesByEmergencyContact(emergencyContact));
    }

    // ==================== UPDATE ====================
    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String employeeId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateEmployee(employeeId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/softDelete/{employeeId}")
    public ResponseEntity<Employee> softDeleteEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.softDeleteEmployee(employeeId));
    }

    @DeleteMapping("/hardDelete/{employeeId}")
    public ResponseEntity<Map<String, String>> hardDeleteEmployee(@PathVariable String employeeId) {
        service.hardDeleteEmployee(employeeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");
        response.put("employeeId", employeeId);
        return ResponseEntity.ok(response);
    }

    // ==================== RESTORE ====================
    @PatchMapping("/restore/{employeeId}")
    public ResponseEntity<Employee> restoreEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.restoreEmployee(employeeId));
    }
}