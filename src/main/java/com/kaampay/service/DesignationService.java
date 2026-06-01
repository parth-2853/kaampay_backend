package com.kaampay.service;

import com.kaampay.entity.Designation;
import com.kaampay.entity.DesignationStatus;
import com.kaampay.repository.DesignationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DesignationService {

    private final DesignationRepository repository;

    public DesignationService(DesignationRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public Designation createDesignation(Designation designation) {
        if (designation.getDesignationId() == null || designation.getDesignationId().isEmpty()) {
            designation.setDesignationId(UUID.randomUUID().toString());
        }

        // Check if designation already exists for this specific company
        if (repository.existsByCompanyIdAndDesignationNameIgnoreCase(designation.getCompanyId(), designation.getDesignationName())) {
            throw new RuntimeException("A designation with this name already exists in this company.");
        }

        return repository.save(designation);
    }

    // READ - Get all
    public List<Designation> getAllDesignations() {
        return repository.findAll();
    }

    // READ - Get by ID
    public Designation getDesignationById(String designationId) {
        return repository.findById(designationId)
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + designationId));
    }

    // READ - Get by Company ID
    public List<Designation> getDesignationsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    // READ - Get by Department ID
    public List<Designation> getDesignationsByDepartmentId(String departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    // READ - Get by Status
    public List<Designation> getDesignationsByStatus(DesignationStatus status) {
        return repository.findByStatus(status);
    }

    // UPDATE - Partial update
    @Transactional
    public Designation updateDesignation(String designationId, Map<String, Object> updates) {
        Designation existingDesignation = getDesignationById(designationId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "designationName":
                        existingDesignation.setDesignationName((String) value);
                        break;
                    case "departmentId":
                        existingDesignation.setDepartmentId((String) value);
                        break;
                    case "description":
                        existingDesignation.setDescription((String) value);
                        break;
                    case "employeeCount":
                        existingDesignation.setEmployeeCount((Integer) value);
                        break;
                    case "status":
                        existingDesignation.setStatus(DesignationStatus.valueOf((String) value));
                        break;
                    default:
                        // Ignore unknown fields
                        break;
                }
            }
        });

        return repository.save(existingDesignation);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteDesignation(String designationId) {
        Designation designation = getDesignationById(designationId);
        repository.delete(designation);
    }

    // DELETE - Soft delete
    @Transactional
    public Designation softDeleteDesignation(String designationId) {
        Designation designation = getDesignationById(designationId);
        designation.setStatus(DesignationStatus.inactive);
        return repository.save(designation);
    }
}