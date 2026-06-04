package com.kaampay.controller;

import com.kaampay.entity.LeaveType;
import com.kaampay.service.LeaveTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaves")
public class LeaveTypeController {

    private final LeaveTypeService service;

    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<LeaveType> createLeaveType(@RequestBody LeaveType leaveType) {
        LeaveType created = service.createLeaveType(leaveType);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/create/batch")
    public ResponseEntity<List<LeaveType>> createMultipleLeaveTypes(@RequestBody List<LeaveType> leaveTypes) {
        List<LeaveType> created = service.createMultipleLeaveTypes(leaveTypes);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ (GET) ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveType>> getAllLeaveTypes() {
        return ResponseEntity.ok(service.getAllLeaveTypes());
    }

    @GetMapping("/get/{leaveTypeId}")
    public ResponseEntity<LeaveType> getLeaveTypeById(@PathVariable String leaveTypeId) {
        return ResponseEntity.ok(service.getLeaveTypeById(leaveTypeId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<LeaveType>> getLeaveTypesByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getLeaveTypesByCompanyId(companyId));
    }

    @GetMapping("/getByCompanyAndIsPaid/{companyId}/{isPaid}")
    public ResponseEntity<List<LeaveType>> getLeaveTypesByCompanyAndIsPaid(
            @PathVariable String companyId,
            @PathVariable String isPaid) {
        return ResponseEntity.ok(service.getLeaveTypesByCompanyAndIsPaid(companyId, isPaid));
    }

    @GetMapping("/getPaidLeavesOnly/{companyId}")
    public ResponseEntity<List<LeaveType>> getPaidLeavesOnly(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getPaidLeavesOnly(companyId));
    }

    @GetMapping("/getUnpaidLeavesOnly/{companyId}")
    public ResponseEntity<List<LeaveType>> getUnpaidLeavesOnly(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getUnpaidLeavesOnly(companyId));
    }

    @GetMapping("/getByCompanyAndCarryForward/{companyId}/{isCarryForward}")
    public ResponseEntity<List<LeaveType>> getLeaveTypesByCompanyAndCarryForward(
            @PathVariable String companyId,
            @PathVariable String isCarryForward) {
        return ResponseEntity.ok(service.getLeaveTypesByCompanyAndCarryForward(companyId, isCarryForward));
    }

    @GetMapping("/getCarryForwardLeavesOnly/{companyId}")
    public ResponseEntity<List<LeaveType>> getCarryForwardLeavesOnly(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getCarryForwardLeavesOnly(companyId));
    }

    @GetMapping("/getByCompanyAndAllocationType/{companyId}/{allocationType}")
    public ResponseEntity<List<LeaveType>> getLeaveTypesByCompanyAndAllocationType(
            @PathVariable String companyId,
            @PathVariable String allocationType) {
        return ResponseEntity.ok(service.getLeaveTypesByCompanyAndAllocationType(companyId, allocationType));
    }

    @GetMapping("/search/{companyId}/{leaveName}")
    public ResponseEntity<List<LeaveType>> searchLeaveTypesByName(
            @PathVariable String companyId,
            @PathVariable String leaveName) {
        return ResponseEntity.ok(service.searchLeaveTypesByName(companyId, leaveName));
    }

    @GetMapping("/getPaidLeavesCount/{companyId}")
    public ResponseEntity<Map<String, Object>> getPaidLeavesCount(@PathVariable String companyId) {
        Long count = service.getPaidLeavesCount(companyId);
        Map<String, Object> response = new HashMap<>();
        response.put("companyId", companyId);
        response.put("paidLeavesCount", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCarryForwardLeavesCount/{companyId}")
    public ResponseEntity<Map<String, Object>> getCarryForwardLeavesCount(@PathVariable String companyId) {
        Long count = service.getCarryForwardLeavesCount(companyId);
        Map<String, Object> response = new HashMap<>();
        response.put("companyId", companyId);
        response.put("carryForwardLeavesCount", count);
        return ResponseEntity.ok(response);
    }

    // ==================== UPDATE (PATCH) ====================
    @PatchMapping("/update/{leaveTypeId}")
    public ResponseEntity<LeaveType> updateLeaveType(
            @PathVariable String leaveTypeId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateLeaveType(leaveTypeId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{leaveTypeId}")
    public ResponseEntity<Map<String, String>> deleteLeaveType(@PathVariable String leaveTypeId) {
        service.deleteLeaveType(leaveTypeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Leave type deleted successfully");
        response.put("leaveTypeId", leaveTypeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteByCompany/{companyId}")
    public ResponseEntity<Map<String, String>> deleteAllLeaveTypesByCompanyId(@PathVariable String companyId) {
        service.deleteAllLeaveTypesByCompanyId(companyId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "All leave types for company deleted successfully");
        response.put("companyId", companyId);
        return ResponseEntity.ok(response);
    }
}