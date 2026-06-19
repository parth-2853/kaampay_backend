package com.kaampay.controller;

import com.kaampay.entity.PaymentLedger;
import com.kaampay.service.PaymentLedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentLedgerController {

    private final PaymentLedgerService service;

    public PaymentLedgerController(PaymentLedgerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentLedger> createPayment(@RequestBody PaymentLedger payment) {
        return new ResponseEntity<>(service.createPayment(payment), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentLedger>> getAllPayments() {
        return ResponseEntity.ok(service.getAllPayments());
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<PaymentLedger> getPaymentById(@PathVariable String paymentId) {
        return ResponseEntity.ok(service.getPaymentById(paymentId));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    public ResponseEntity<List<PaymentLedger>> getPaymentsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getPaymentsByEmployeeId(employeeId));
    }

    @PatchMapping("/update/{paymentId}")
    public ResponseEntity<PaymentLedger> updatePayment(
            @PathVariable String paymentId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updatePayment(paymentId, updates));
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Map<String, String>> deletePayment(@PathVariable String paymentId) {
        service.deletePayment(paymentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment deleted successfully");
        response.put("paymentId", paymentId);
        return ResponseEntity.ok(response);
    }
}