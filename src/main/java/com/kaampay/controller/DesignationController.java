package com.kaampay.controller;

import com.kaampay.entity.Designation;
import com.kaampay.entity.DesignationStatus;
import com.kaampay.service.DesignationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/designations")
public class DesignationController {

    private final DesignationService service;

    public DesignationController(DesignationService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) {
        Designation created = service.createDesignation(designation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<Designation>> getAllDesignations() {
        return ResponseEntity.ok(service.getAllDesignations());
    }

    @GetMapping("/get/{designationId}")
    public ResponseEntity<Designation> getDesignationById(@PathVariable String designationId) {
        return ResponseEntity.ok(service.getDesignationById(designationId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<Designation>> getDesignationsByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getDesignationsByCompanyId(companyId));
    }

    @GetMapping("/getByDepartment/{departmentId}")
    public ResponseEntity<List<Designation>> getDesignationsByDepartmentId(@PathVariable String departmentId) {
        return ResponseEntity.ok(service.getDesignationsByDepartmentId(departmentId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<Designation>> getDesignationsByStatus(@PathVariable DesignationStatus status) {
        return ResponseEntity.ok(service.getDesignationsByStatus(status));
    }

    // ==================== UPDATE ====================
    @PatchMapping("/update/{designationId}")
    public ResponseEntity<Designation> updateDesignation(
            @PathVariable String designationId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateDesignation(designationId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{designationId}")
    public ResponseEntity<Map<String, String>> deleteDesignation(@PathVariable String designationId) {
        service.deleteDesignation(designationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Designation deleted successfully");
        response.put("designationId", designationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/softDelete/{designationId}")
    public ResponseEntity<Designation> softDeleteDesignation(@PathVariable String designationId) {
        return ResponseEntity.ok(service.softDeleteDesignation(designationId));
    }
}