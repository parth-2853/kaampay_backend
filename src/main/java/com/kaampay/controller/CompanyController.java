package com.kaampay.controller;

import com.kaampay.entity.Company;
import com.kaampay.entity.CompanyStatus;
import com.kaampay.entity.SubscriptionStatus;
import com.kaampay.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company created = service.createCompany(company);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ (GET) ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(service.getAllCompanies());
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getCompanyById(companyId));
    }

    @GetMapping("/getByName/{companyName}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String companyName) {
        return ResponseEntity.ok(service.getCompanyByName(companyName));
    }

    @GetMapping("/getByPhoneNumber/{phoneNumber}")
    public ResponseEntity<List<Company>> getCompaniesByPhoneNumber(@PathVariable Long phoneNumber) {
        return ResponseEntity.ok(service.getCompaniesByPhoneNumber(phoneNumber));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<Company>> getCompaniesByStatus(@PathVariable CompanyStatus status) {
        return ResponseEntity.ok(service.getCompaniesByStatus(status));
    }

    @GetMapping("/getBySubscriptionStatus/{subscriptionStatus}")
    public ResponseEntity<List<Company>> getCompaniesBySubscriptionStatus(@PathVariable SubscriptionStatus subscriptionStatus) {
        return ResponseEntity.ok(service.getCompaniesBySubscriptionStatus(subscriptionStatus));
    }

    @GetMapping("/getByState/{state}")
    public ResponseEntity<List<Company>> getCompaniesByState(@PathVariable String state) {
        return ResponseEntity.ok(service.getCompaniesByState(state));
    }

    @GetMapping("/getByBusinessType/{businessType}")
    public ResponseEntity<List<Company>> getCompaniesByBusinessType(@PathVariable String businessType) {
        return ResponseEntity.ok(service.getCompaniesByBusinessType(businessType));
    }

    @GetMapping("/getExpiredSubscriptions")
    public ResponseEntity<List<Company>> getExpiredSubscriptions() {
        return ResponseEntity.ok(service.getExpiredSubscriptions());
    }

    @GetMapping("/getExpiringIn/{days}")
    public ResponseEntity<List<Company>> getSubscriptionsExpiringInDays(@PathVariable int days) {
        return ResponseEntity.ok(service.getSubscriptionsExpiringInDays(days));
    }

    // ==================== UPDATE (PATCH - Partial Update) ====================
    @PatchMapping("/update/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable String companyId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateCompany(companyId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<Map<String, String>> deleteCompany(@PathVariable String companyId) {
        service.deleteCompany(companyId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Company deleted successfully");
        response.put("companyId", companyId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/softDelete/{companyId}")
    public ResponseEntity<Company> softDeleteCompany(@PathVariable String companyId) {
        return ResponseEntity.ok(service.softDeleteCompany(companyId));
    }
}