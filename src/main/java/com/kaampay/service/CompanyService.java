package com.kaampay.service;

import com.kaampay.entity.Company;
import com.kaampay.entity.CompanyStatus;
import com.kaampay.entity.SubscriptionStatus;
import com.kaampay.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public Company createCompany(Company company) {
        if (company.getCompanyId() == null || company.getCompanyId().isEmpty()) {
            company.setCompanyId(UUID.randomUUID().toString());
        }

        // --- NEW FIX: Convert empty strings to null so they don't trigger duplicates ---
        if (company.getGstNumber() != null && company.getGstNumber().trim().isEmpty()) {
            company.setGstNumber(null);
        }

        // Now, it will only check the database if a REAL GST number was provided
        if (company.getGstNumber() != null && repository.existsByGstNumber(company.getGstNumber())) {
            throw new RuntimeException("Company with this GST number already exists");
        }
        // --------------------------------------------------------------------------------

        if (repository.existsByCompanyName(company.getCompanyName())) {
            throw new RuntimeException("Company with this name already exists");
        }

        return repository.save(company);
    }

    // READ - Get all
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    // READ - Get by ID
    public Company getCompanyById(String companyId) {
        return repository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));
    }

    // READ - Get by Name
    public Company getCompanyByName(String companyName) {
        return repository.findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Company not found with name: " + companyName));
    }

    // READ - Get by Phone Number
    public List<Company> getCompaniesByPhoneNumber(Long phoneNumber) {
        List<Company> companies = repository.findByPhoneNumber(phoneNumber);
        if (companies.isEmpty()) {
            throw new RuntimeException("No companies found with phone number: " + phoneNumber);
        }
        return companies;
    }

    // READ - Get by Status
    public List<Company> getCompaniesByStatus(CompanyStatus status) {
        return repository.findByStatus(status);
    }

    // READ - Get by Subscription Status
    public List<Company> getCompaniesBySubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        return repository.findBySubscriptionStatus(subscriptionStatus);
    }

    // READ - Get by State
    public List<Company> getCompaniesByState(String state) {
        return repository.findByState(state);
    }

    // READ - Get by Business Type
    public List<Company> getCompaniesByBusinessType(String businessType) {
        return repository.findByBusinessType(businessType);
    }

    // READ - Get Expired Subscriptions
    public List<Company> getExpiredSubscriptions() {
        return repository.findExpiredSubscriptions(LocalDateTime.now());
    }

    // READ - Get Expiring in N Days
    public List<Company> getSubscriptionsExpiringInDays(int days) {
        LocalDateTime now = LocalDateTime.now();
        return repository.findSubscriptionsExpiringBetween(now, now.plusDays(days));
    }

    // UPDATE - Partial update (only updates fields that are provided)
    @Transactional
    public Company updateCompany(String companyId, Map<String, Object> updates) {
        Company existingCompany = getCompanyById(companyId);

        // Update only the fields that are present in the request
        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "companyName":
                        existingCompany.setCompanyName((String) value);
                        break;
                    case "businessType":
                        existingCompany.setBusinessType((String) value);
                        break;
                    case "state":
                        existingCompany.setState((String) value);
                        break;
                    case "gstNumber":
                        existingCompany.setGstNumber((String) value);
                        break;
                    case "phoneNumber":
                        existingCompany.setPhoneNumber(value instanceof Integer ? ((Integer) value).longValue() : (Long) value);
                        break;
                    case "address":
                        existingCompany.setAddress((String) value);
                        break;
                    case "profileImageBase64":
                        existingCompany.setProfileImageBase64((String) value);
                        break;
                    case "signatureBase64":
                        existingCompany.setSignatureBase64((String) value);
                        break;
                    case "referralCode":
                        existingCompany.setReferralCode((String) value);
                        break;
                    case "latitude":
                        existingCompany.setLatitude(new java.math.BigDecimal(value.toString()));
                        break;
                    case "longitude":
                        existingCompany.setLongitude(new java.math.BigDecimal(value.toString()));
                        break;
                    case "attendanceRadius":
                        existingCompany.setAttendanceRadius((Integer) value);
                        break;
                    case "status":
                        existingCompany.setStatus(CompanyStatus.valueOf((String) value));
                        break;
                    case "subscriptionStatus":
                        existingCompany.setSubscriptionStatus(SubscriptionStatus.valueOf((String) value));
                        break;
                    case "subscriptionStartDate":
                        existingCompany.setSubscriptionStartDate(LocalDateTime.parse((String) value));
                        break;
                    case "subscriptionEndDate":
                        existingCompany.setSubscriptionEndDate(LocalDateTime.parse((String) value));
                        break;
                    case "currentPlan":
                        existingCompany.setCurrentPlan((String) value);
                        break;
                    default:
                        // Unknown field, ignore
                        break;
                }
            }
        });

        return repository.save(existingCompany);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteCompany(String companyId) {
        Company company = getCompanyById(companyId);
        repository.delete(company);
    }

    // DELETE - Soft delete
    @Transactional
    public Company softDeleteCompany(String companyId) {
        Company company = getCompanyById(companyId);
        company.setStatus(CompanyStatus.inactive);
        return repository.save(company);
    }
}