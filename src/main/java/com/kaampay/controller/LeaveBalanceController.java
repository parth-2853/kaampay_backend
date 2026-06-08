package com.kaampay.controller;

import com.kaampay.entity.LeaveBalance;
import com.kaampay.service.LeaveBalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave-balances")
public class LeaveBalanceController {

    private final LeaveBalanceService service;

    public LeaveBalanceController(LeaveBalanceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<LeaveBalance> createBalance(@RequestBody LeaveBalance balance) {
        LeaveBalance created = service.createBalance(balance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveBalance>> getAllBalances() {
        return ResponseEntity.ok(service.getAllBalances());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveBalance> getBalanceById(@PathVariable String id) {
        return ResponseEntity.ok(service.getBalanceById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveBalance>> getBalancesByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getBalancesByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/year/{year}")
    public ResponseEntity<List<LeaveBalance>> getEmployeeBalancesForYear(
            @PathVariable String employeeId,
            @PathVariable Integer year) {
        return ResponseEntity.ok(service.getEmployeeBalancesForYear(employeeId, year));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<LeaveBalance> updateBalance(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateBalance(id, updates));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable String id) {
        service.deleteBalance(id);
        return ResponseEntity.ok().build();
    }
}