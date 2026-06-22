package com.kaampay.controller;

import com.kaampay.entity.CompanySubscription;
import com.kaampay.entity.SubscriptionStatus;
import com.kaampay.service.CompanySubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscription")
public class CompanySubscriptionController {

    private final CompanySubscriptionService service;

    public CompanySubscriptionController(CompanySubscriptionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<CompanySubscription> createSubscription(@RequestBody CompanySubscription subscription) {
        return new ResponseEntity<>(service.createSubscription(subscription), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanySubscription>> getAllSubscriptions() {
        return ResponseEntity.ok(service.getAllSubscriptions());
    }

    @GetMapping("/get/{subscriptionId}")
    public ResponseEntity<CompanySubscription> getSubscriptionById(@PathVariable String subscriptionId) {
        return ResponseEntity.ok(service.getSubscriptionById(subscriptionId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<CompanySubscription>> getSubscriptionsByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getSubscriptionsByCompanyId(companyId));
    }

    @GetMapping("/getByPlan/{planId}")
    public ResponseEntity<List<CompanySubscription>> getSubscriptionsByPlanId(@PathVariable String planId) {
        return ResponseEntity.ok(service.getSubscriptionsByPlanId(planId));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<CompanySubscription>> getSubscriptionsByStatus(@PathVariable SubscriptionStatus status) {
        return ResponseEntity.ok(service.getSubscriptionsByStatus(status));
    }

    @PatchMapping("/update/{subscriptionId}")
    public ResponseEntity<CompanySubscription> updateSubscription(
            @PathVariable String subscriptionId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateSubscription(subscriptionId, updates));
    }

    @DeleteMapping("/delete/{subscriptionId}")
    public ResponseEntity<Map<String, String>> deleteSubscription(@PathVariable String subscriptionId) {
        service.deleteSubscription(subscriptionId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Company subscription deleted successfully");
        response.put("subscriptionId", subscriptionId);
        return ResponseEntity.ok(response);
    }
}