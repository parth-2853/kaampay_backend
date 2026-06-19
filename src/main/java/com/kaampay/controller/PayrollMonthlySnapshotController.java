package com.kaampay.controller;

import com.kaampay.entity.PayrollMonthlySnapshot;
import com.kaampay.entity.PayrollStatus;
import com.kaampay.service.PayrollMonthlySnapshotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payroll-snapshot")
public class PayrollMonthlySnapshotController {

    private final PayrollMonthlySnapshotService service;

    public PayrollMonthlySnapshotController(PayrollMonthlySnapshotService service) {
        this.service = service;
    }

    // Handles both Create and Full Update (re-syncing the month's math)
    @PostMapping("/sync")
    public ResponseEntity<PayrollMonthlySnapshot> syncSnapshot(@RequestBody PayrollMonthlySnapshot snapshot) {
        return new ResponseEntity<>(service.saveOrUpdateSnapshot(snapshot), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PayrollMonthlySnapshot>> getAllSnapshots() {
        return ResponseEntity.ok(service.getAllSnapshots());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PayrollMonthlySnapshot> getSnapshotById(@PathVariable String id) {
        return ResponseEntity.ok(service.getSnapshotById(id));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    public ResponseEntity<List<PayrollMonthlySnapshot>> getSnapshotsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getSnapshotsByEmployeeId(employeeId));
    }

    @GetMapping("/getByPeriod")
    public ResponseEntity<PayrollMonthlySnapshot> getSnapshotByPeriod(
            @RequestParam String employeeId,
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return ResponseEntity.ok(service.getSnapshotByEmployeeAndPeriod(employeeId, month, year));
    }

    // Special quick endpoint just to change status from draft -> finalized -> paid
    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<PayrollMonthlySnapshot> updateStatus(
            @PathVariable String id,
            @RequestParam PayrollStatus status) {
        return ResponseEntity.ok(service.updateSnapshotStatus(id, status));
    }
}