package com.kaampay.controller;

import com.kaampay.entity.CompanyBranch;
import com.kaampay.entity.BranchStatus;
import com.kaampay.service.CompanyBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/branches")
public class CompanyBranchController {

    private final CompanyBranchService service;

    public CompanyBranchController(CompanyBranchService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<CompanyBranch> createBranch(@RequestBody CompanyBranch branch) {
        CompanyBranch created = service.createBranch(branch);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ (GET) ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyBranch>> getAllBranches() {
        return ResponseEntity.ok(service.getAllBranches());
    }

    @GetMapping("/get/{branchId}")
    public ResponseEntity<CompanyBranch> getBranchById(@PathVariable String branchId) {
        return ResponseEntity.ok(service.getBranchById(branchId));
    }

    @GetMapping("/getByCompanyId/{companyId}")
    public ResponseEntity<List<CompanyBranch>> getBranchesByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getBranchesByCompanyId(companyId));
    }

    @GetMapping("/getMainBranch/{companyId}")
    public ResponseEntity<List<CompanyBranch>> getMainBranchByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getMainBranchByCompanyId(companyId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<CompanyBranch>> getBranchesByStatus(@PathVariable BranchStatus status) {
        return ResponseEntity.ok(service.getBranchesByStatus(status));
    }

    // ==================== UPDATE (PATCH - Partial Update) ====================
    @PatchMapping("/update/{branchId}")
    public ResponseEntity<CompanyBranch> updateBranch(@PathVariable String branchId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateBranch(branchId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Map<String, String>> deleteBranch(@PathVariable String branchId) {
        service.deleteBranch(branchId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Branch deleted successfully");
        response.put("branchId", branchId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/softDelete/{branchId}")
    public ResponseEntity<CompanyBranch> softDeleteBranch(@PathVariable String branchId) {
        return ResponseEntity.ok(service.softDeleteBranch(branchId));
    }
}