package com.kaampay.controller;

import com.kaampay.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order/{planId}")
    public ResponseEntity<Map<String, String>> createOrder(@PathVariable String planId) {
        try {
            String orderId = paymentService.createRazorpayOrder(planId);
            Map<String, String> response = new HashMap<>();
            response.put("orderId", orderId);
            return ResponseEntity.ok(response);
        } catch (RazorpayException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> payload) {
        String orderId = payload.get("razorpay_order_id");
        String paymentId = payload.get("razorpay_payment_id");
        String signature = payload.get("razorpay_signature");

        boolean isValid = paymentService.verifyPaymentSignature(orderId, paymentId, signature);

        if (isValid) {
            return ResponseEntity.ok("Payment Verified Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
        }
    }
}