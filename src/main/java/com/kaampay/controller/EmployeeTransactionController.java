package com.kaampay.controller;

import com.kaampay.entity.EmployeeTransaction;
import com.kaampay.service.EmployeeTransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class EmployeeTransactionController {

    private final EmployeeTransactionService service;

    public EmployeeTransactionController(EmployeeTransactionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeTransaction> createTransaction(@RequestBody EmployeeTransaction transaction) {
        return new ResponseEntity<>(service.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeTransaction>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/get/{transactionId}")
    public ResponseEntity<EmployeeTransaction> getTransactionById(@PathVariable String transactionId) {
        return ResponseEntity.ok(service.getTransactionById(transactionId));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    public ResponseEntity<List<EmployeeTransaction>> getTransactionsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getTransactionsByEmployeeId(employeeId));
    }

    @GetMapping("/getByEmployeeAndDateRange")
    public ResponseEntity<List<EmployeeTransaction>> getTransactionsByDateRange(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getTransactionsByDateRange(employeeId, startDate, endDate));
    }

    @PatchMapping("/update/{transactionId}")
    public ResponseEntity<EmployeeTransaction> updateTransaction(
            @PathVariable String transactionId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateTransaction(transactionId, updates));
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Map<String, String>> deleteTransaction(@PathVariable String transactionId) {
        service.deleteTransaction(transactionId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transaction deleted successfully");
        response.put("transactionId", transactionId);
        return ResponseEntity.ok(response);
    }
}