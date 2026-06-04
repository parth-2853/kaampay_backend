package com.kaampay.service;

import com.kaampay.entity.LeaveType;
import com.kaampay.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository repository;
    private final CompanyService companyService;

    public LeaveTypeService(LeaveTypeRepository repository, CompanyService companyService) {
        this.repository = repository;
        this.companyService = companyService;
    }

    // Helper method to validate isPaid/isCarryForward values
    private void validateBooleanString(String value, String fieldName) {
        if (value != null && !value.isEmpty()) {
            if (!value.equals("true") && !value.equals("false")) {
                throw new RuntimeException(fieldName + " must be 'true' or 'false'");
            }
        }
    }

    // CREATE
    @Transactional
    public LeaveType createLeaveType(LeaveType leaveType) {
        // Validate company exists
        companyService.getCompanyById(leaveType.getCompanyId());

        // Validate leave name
        if (leaveType.getLeaveName() == null || leaveType.getLeaveName().trim().isEmpty()) {
            throw new RuntimeException("Leave name is required");
        }

        // Check for duplicate leave type for the same company
        if (repository.existsByCompanyIdAndLeaveName(leaveType.getCompanyId(), leaveType.getLeaveName())) {
            throw new RuntimeException("Leave type with name '" + leaveType.getLeaveName() + "' already exists for this company");
        }

        // Validate total days
        if (leaveType.getTotalDays() != null && leaveType.getTotalDays() < 0) {
            throw new RuntimeException("Total days cannot be negative");
        }

        // Validate isPaid and isCarryForward values
        validateBooleanString(leaveType.getIsPaid(), "isPaid");
        validateBooleanString(leaveType.getIsCarryForward(), "isCarryForward");

        // Validate allocation type
        if (leaveType.getAllocationType() != null && !leaveType.getAllocationType().isEmpty()) {
            String validAllocationTypes = "yearly,monthly,quarterly,half_yearly";
            if (!validAllocationTypes.contains(leaveType.getAllocationType().toLowerCase())) {
                throw new RuntimeException("Allocation type must be one of: yearly, monthly, quarterly, half_yearly");
            }
        }

        if (leaveType.getLeaveTypeId() == null || leaveType.getLeaveTypeId().isEmpty()) {
            leaveType.setLeaveTypeId(UUID.randomUUID().toString());
        }

        // Set defaults if null
        if (leaveType.getTotalDays() == null) leaveType.setTotalDays(0);
        if (leaveType.getIsPaid() == null || leaveType.getIsPaid().isEmpty()) leaveType.setIsPaid("true");
        if (leaveType.getIsCarryForward() == null || leaveType.getIsCarryForward().isEmpty()) leaveType.setIsCarryForward("false");

        return repository.save(leaveType);
    }

    // CREATE multiple leave types at once
    @Transactional
    public List<LeaveType> createMultipleLeaveTypes(List<LeaveType> leaveTypes) {
        return leaveTypes.stream()
                .map(this::createLeaveType)
                .toList();
    }

    // READ - Get all leave types
    public List<LeaveType> getAllLeaveTypes() {
        return repository.findAll();
    }

    // READ - Get by ID
    public LeaveType getLeaveTypeById(String leaveTypeId) {
        return repository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave type not found with id: " + leaveTypeId));
    }

    // READ - Get by Company ID
    public List<LeaveType> getLeaveTypesByCompanyId(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findByCompanyIdOrderByLeaveNameAsc(companyId);
    }

    // READ - Get by Company ID and Is Paid
    public List<LeaveType> getLeaveTypesByCompanyAndIsPaid(String companyId, String isPaid) {
        companyService.getCompanyById(companyId);
        validateBooleanString(isPaid, "isPaid");
        return repository.findByCompanyIdAndIsPaid(companyId, isPaid);
    }

    // READ - Get paid leaves only
    public List<LeaveType> getPaidLeavesOnly(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findPaidLeavesByCompany(companyId);
    }

    // READ - Get unpaid leaves only
    public List<LeaveType> getUnpaidLeavesOnly(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findUnpaidLeavesByCompany(companyId);
    }

    // READ - Get by Company ID and Is Carry Forward
    public List<LeaveType> getLeaveTypesByCompanyAndCarryForward(String companyId, String isCarryForward) {
        companyService.getCompanyById(companyId);
        validateBooleanString(isCarryForward, "isCarryForward");
        return repository.findByCompanyIdAndIsCarryForward(companyId, isCarryForward);
    }

    // READ - Get carry forward leaves only
    public List<LeaveType> getCarryForwardLeavesOnly(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findCarryForwardLeavesByCompany(companyId);
    }

    // READ - Get by Company ID and Allocation Type
    public List<LeaveType> getLeaveTypesByCompanyAndAllocationType(String companyId, String allocationType) {
        companyService.getCompanyById(companyId);
        return repository.findByCompanyIdAndAllocationType(companyId, allocationType);
    }

    // READ - Search leave types by name
    public List<LeaveType> searchLeaveTypesByName(String companyId, String leaveName) {
        companyService.getCompanyById(companyId);
        List<LeaveType> leaveTypes = repository.findByCompanyIdAndLeaveNameContainingIgnoreCase(companyId, leaveName);
        if (leaveTypes.isEmpty()) {
            throw new RuntimeException("No leave types found with name containing: " + leaveName);
        }
        return leaveTypes;
    }

    // READ - Get paid leaves count
    public Long getPaidLeavesCount(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.countPaidLeavesByCompany(companyId);
    }

    // READ - Get carry forward leaves count
    public Long getCarryForwardLeavesCount(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.countCarryForwardLeavesByCompany(companyId);
    }

    // UPDATE - Partial update
    @Transactional
    public LeaveType updateLeaveType(String leaveTypeId, Map<String, Object> updates) {
        LeaveType existingLeaveType = getLeaveTypeById(leaveTypeId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "leaveName":
                        String newName = (String) value;
                        // Check if new name conflicts with existing (excluding current)
                        if (repository.existsByCompanyIdAndLeaveName(existingLeaveType.getCompanyId(), newName) &&
                                !existingLeaveType.getLeaveName().equals(newName)) {
                            throw new RuntimeException("Leave type with name '" + newName + "' already exists for this company");
                        }
                        existingLeaveType.setLeaveName(newName);
                        break;
                    case "totalDays":
                        Integer days = value instanceof Integer ? (Integer) value : Integer.parseInt(value.toString());
                        if (days < 0) {
                            throw new RuntimeException("Total days cannot be negative");
                        }
                        existingLeaveType.setTotalDays(days);
                        break;
                    case "isPaid":
                        String isPaid = value.toString();
                        validateBooleanString(isPaid, "isPaid");
                        existingLeaveType.setIsPaid(isPaid);
                        break;
                    case "isCarryForward":
                        String isCarryForward = value.toString();
                        validateBooleanString(isCarryForward, "isCarryForward");
                        existingLeaveType.setIsCarryForward(isCarryForward);
                        break;
                    case "description":
                        existingLeaveType.setDescription((String) value);
                        break;
                    case "allocationType":
                        String allocationType = (String) value;
                        if (allocationType != null && !allocationType.isEmpty()) {
                            String validAllocationTypes = "yearly,monthly,quarterly,half_yearly";
                            if (!validAllocationTypes.contains(allocationType.toLowerCase())) {
                                throw new RuntimeException("Allocation type must be one of: yearly, monthly, quarterly, half_yearly");
                            }
                        }
                        existingLeaveType.setAllocationType(allocationType);
                        break;
                    default:
                        // Unknown field, ignore
                        break;
                }
            }
        });

        return repository.save(existingLeaveType);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteLeaveType(String leaveTypeId) {
        LeaveType leaveType = getLeaveTypeById(leaveTypeId);
        repository.delete(leaveType);
    }

    // DELETE - Delete all leave types for a company
    @Transactional
    public void deleteAllLeaveTypesByCompanyId(String companyId) {
        companyService.getCompanyById(companyId);
        repository.deleteByCompanyId(companyId);
    }
}