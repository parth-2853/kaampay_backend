package com.kaampay.controller;

import com.kaampay.entity.LeaveRequest;
import com.kaampay.service.LeaveRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService service;

    public LeaveRequestController(LeaveRequestService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<LeaveRequest> createRequest(@RequestBody LeaveRequest request) {
        LeaveRequest created = service.createRequest(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveRequest>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveRequest> getRequestById(@PathVariable String id) {
        return ResponseEntity.ok(service.getRequestById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getRequestsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getRequestsByEmployeeId(employeeId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<LeaveRequest>> getRequestsByCompany(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getRequestsByCompanyId(companyId));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<LeaveRequest> updateRequest(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateRequest(id, updates));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
        service.deleteRequest(id);
        return ResponseEntity.ok().build();
    }
}