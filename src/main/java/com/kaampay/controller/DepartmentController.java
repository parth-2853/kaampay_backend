package com.kaampay.controller;

import com.kaampay.entity.Department;
import com.kaampay.entity.DepartmentStatus;
import com.kaampay.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = service.createDepartment(department);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @GetMapping("/get/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String departmentId) {
        return ResponseEntity.ok(service.getDepartmentById(departmentId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<Department>> getDepartmentsByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getDepartmentsByCompanyId(companyId));
    }

    @GetMapping("/getByBranch/{branchId}")
    public ResponseEntity<List<Department>> getDepartmentsByBranchId(@PathVariable String branchId) {
        return ResponseEntity.ok(service.getDepartmentsByBranchId(branchId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<Department>> getDepartmentsByStatus(@PathVariable DepartmentStatus status) {
        return ResponseEntity.ok(service.getDepartmentsByStatus(status));
    }

    // ==================== UPDATE ====================
    @PatchMapping("/update/{departmentId}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable String departmentId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateDepartment(departmentId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable String departmentId) {
        service.deleteDepartment(departmentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Department deleted successfully");
        response.put("departmentId", departmentId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/softDelete/{departmentId}")
    public ResponseEntity<Department> softDeleteDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(service.softDeleteDepartment(departmentId));
    }
}