package com.kaampay.service;

import com.kaampay.entity.Department;
import com.kaampay.entity.DepartmentStatus;
import com.kaampay.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public Department createDepartment(Department department) {
        if (department.getDepartmentId() == null || department.getDepartmentId().isEmpty()) {
            department.setDepartmentId(UUID.randomUUID().toString());
        }

        // Check if department already exists for this specific company
        if (repository.existsByCompanyIdAndDepartmentNameIgnoreCase(department.getCompanyId(), department.getDepartmentName())) {
            throw new RuntimeException("A department with this name already exists in this company.");
        }

        return repository.save(department);
    }

    // READ - Get all
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    // READ - Get by ID
    public Department getDepartmentById(String departmentId) {
        return repository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
    }

    // READ - Get by Company ID
    public List<Department> getDepartmentsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    // READ - Get by Branch ID
    public List<Department> getDepartmentsByBranchId(String branchId) {
        return repository.findByBranchId(branchId);
    }

    // READ - Get by Status
    public List<Department> getDepartmentsByStatus(DepartmentStatus status) {
        return repository.findByStatus(status);
    }

    // UPDATE - Partial update
    @Transactional
    public Department updateDepartment(String departmentId, Map<String, Object> updates) {
        Department existingDepartment = getDepartmentById(departmentId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "departmentName":
                        existingDepartment.setDepartmentName((String) value);
                        break;
                    case "departmentHead":
                        existingDepartment.setDepartmentHead((String) value);
                        break;
                    case "description":
                        existingDepartment.setDescription((String) value);
                        break;
                    case "employeeCount":
                        existingDepartment.setEmployeeCount((Integer) value);
                        break;
                    case "branchId":
                        existingDepartment.setBranchId((String) value);
                        break;
                    case "status":
                        existingDepartment.setStatus(DepartmentStatus.valueOf((String) value));
                        break;
                    default:
                        // Ignore unknown fields
                        break;
                }
            }
        });

        return repository.save(existingDepartment);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteDepartment(String departmentId) {
        Department department = getDepartmentById(departmentId);
        repository.delete(department);
    }

    // DELETE - Soft delete
    @Transactional
    public Department softDeleteDepartment(String departmentId) {
        Department department = getDepartmentById(departmentId);
        department.setStatus(DepartmentStatus.inactive);
        return repository.save(department);
    }
}