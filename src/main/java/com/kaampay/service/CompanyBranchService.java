package com.kaampay.service;

import com.kaampay.entity.CompanyBranch;
import com.kaampay.entity.BranchStatus;
import com.kaampay.repository.CompanyBranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyBranchService {

    private final CompanyBranchRepository repository;

    public CompanyBranchService(CompanyBranchRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public CompanyBranch createBranch(CompanyBranch branch) {
        if (branch.getBranchId() == null || branch.getBranchId().isEmpty()) {
            branch.setBranchId(UUID.randomUUID().toString());
        }

        // Prevent empty strings from triggering duplicate constraint issues
        if (branch.getBranchCode() != null && branch.getBranchCode().trim().isEmpty()) {
            branch.setBranchCode(null);
        }

        if (branch.getBranchCode() != null && repository.existsByBranchCode(branch.getBranchCode())) {
            throw new RuntimeException("Branch with this code already exists");
        }

        return repository.save(branch);
    }

    // READ - Get all
    public List<CompanyBranch> getAllBranches() {
        return repository.findAll();
    }

    // READ - Get by ID
    public CompanyBranch getBranchById(String branchId) {
        return repository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));
    }

    // READ - Get by Company ID
    public List<CompanyBranch> getBranchesByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    // READ - Get Main Branch for Company
    public List<CompanyBranch> getMainBranchByCompanyId(String companyId) {
        return repository.findByCompanyIdAndIsMainBranchTrue(companyId);
    }

    // READ - Get by Status
    public List<CompanyBranch> getBranchesByStatus(BranchStatus status) {
        return repository.findByStatus(status);
    }

    // UPDATE - Partial update
    @Transactional
    public CompanyBranch updateBranch(String branchId, Map<String, Object> updates) {
        CompanyBranch existingBranch = getBranchById(branchId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "branchName":
                        existingBranch.setBranchName((String) value);
                        break;
                    case "branchCode":
                        existingBranch.setBranchCode((String) value);
                        break;
                    case "phone":
                        existingBranch.setPhone((String) value);
                        break;
                    case "whatsapp":
                        existingBranch.setWhatsapp((String) value);
                        break;
                    case "email":
                        existingBranch.setEmail((String) value);
                        break;
                    case "address":
                        existingBranch.setAddress((String) value);
                        break;
                    case "latitude":
                        existingBranch.setLatitude(new BigDecimal(value.toString()));
                        break;
                    case "longitude":
                        existingBranch.setLongitude(new BigDecimal(value.toString()));
                        break;
                    case "attendanceRadius":
                        existingBranch.setAttendanceRadius((Integer) value);
                        break;
                    case "isMainBranch":
                        existingBranch.setIsMainBranch((Boolean) value);
                        break;
                    case "status":
                        existingBranch.setStatus(BranchStatus.valueOf((String) value));
                        break;
                    case "macAddress":
                        existingBranch.setMacAddress((String) value);
                        break;
                    default:
                        // Unknown field, ignore
                        break;
                }
            }
        });

        return repository.save(existingBranch);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteBranch(String branchId) {
        CompanyBranch branch = getBranchById(branchId);
        repository.delete(branch);
    }

    // DELETE - Soft delete
    @Transactional
    public CompanyBranch softDeleteBranch(String branchId) {
        CompanyBranch branch = getBranchById(branchId);
        branch.setStatus(BranchStatus.inactive);
        return repository.save(branch);
    }
}