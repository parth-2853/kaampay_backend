package com.kaampay.controller;

import com.kaampay.entity.SubscriptionPlan;
import com.kaampay.service.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plan")
public class SubscriptionPlanController {

    private final SubscriptionPlanService service;

    public SubscriptionPlanController(SubscriptionPlanService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<SubscriptionPlan> createPlan(@RequestBody SubscriptionPlan plan) {
        return new ResponseEntity<>(service.createPlan(plan), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubscriptionPlan>> getAllPlans() {
        return ResponseEntity.ok(service.getAllPlans());
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<SubscriptionPlan>> getActivePlans() {
        return ResponseEntity.ok(service.getActivePlans());
    }

    @GetMapping("/get/{planId}")
    public ResponseEntity<SubscriptionPlan> getPlanById(@PathVariable String planId) {
        return ResponseEntity.ok(service.getPlanById(planId));
    }

    @GetMapping("/getByCode/{planCode}")
    public ResponseEntity<SubscriptionPlan> getPlanByCode(@PathVariable String planCode) {
        return ResponseEntity.ok(service.getPlanByCode(planCode));
    }

    @PatchMapping("/update/{planId}")
    public ResponseEntity<SubscriptionPlan> updatePlan(
            @PathVariable String planId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updatePlan(planId, updates));
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Map<String, String>> deletePlan(@PathVariable String planId) {
        service.deletePlan(planId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subscription plan deleted successfully");
        response.put("planId", planId);
        return ResponseEntity.ok(response);
    }
}