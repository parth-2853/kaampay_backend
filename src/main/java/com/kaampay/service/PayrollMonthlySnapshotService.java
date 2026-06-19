package com.kaampay.service;

import com.kaampay.entity.PayrollMonthlySnapshot;
import com.kaampay.entity.PayrollStatus;
import com.kaampay.repository.PayrollMonthlySnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayrollMonthlySnapshotService {

    private final PayrollMonthlySnapshotRepository repository;

    public PayrollMonthlySnapshotService(PayrollMonthlySnapshotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PayrollMonthlySnapshot saveOrUpdateSnapshot(PayrollMonthlySnapshot snapshot) {
        // Since snapshots are unique per month/year for an employee, check if it exists first
        Optional<PayrollMonthlySnapshot> existingOpt = repository.findByEmployeeIdAndMonthAndYear(
                snapshot.getEmployeeId(), snapshot.getMonth(), snapshot.getYear()
        );

        if (existingOpt.isPresent()) {
            PayrollMonthlySnapshot existing = existingOpt.get();
            // Do not override if already finalized/paid unless forced
            if (existing.getStatus() != PayrollStatus.draft) {
                throw new RuntimeException("Cannot overwrite a finalized or paid payroll snapshot.");
            }
            // Update existing fields manually to preserve ID and CreatedAt
            existing.setPayableDays(snapshot.getPayableDays());
            existing.setBaseSalary(snapshot.getBaseSalary());
            existing.setEarnedSalary(snapshot.getEarnedSalary());
            existing.setTotalDynamicEarnings(snapshot.getTotalDynamicEarnings());
            existing.setTotalDynamicDeductions(snapshot.getTotalDynamicDeductions());
            existing.setTotalOvertimePay(snapshot.getTotalOvertimePay());
            existing.setNetPayable(snapshot.getNetPayable());
            existing.setStatus(snapshot.getStatus());
            return repository.save(existing);
        } else {
            if (snapshot.getSnapshotId() == null || snapshot.getSnapshotId().isEmpty()) {
                snapshot.setSnapshotId(UUID.randomUUID().toString());
            }
            return repository.save(snapshot);
        }
    }

    public List<PayrollMonthlySnapshot> getAllSnapshots() {
        return repository.findAll();
    }

    public PayrollMonthlySnapshot getSnapshotById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Snapshot not found with id: " + id));
    }

    public List<PayrollMonthlySnapshot> getSnapshotsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public PayrollMonthlySnapshot getSnapshotByEmployeeAndPeriod(String employeeId, Integer month, Integer year) {
        return repository.findByEmployeeIdAndMonthAndYear(employeeId, month, year)
                .orElseThrow(() -> new RuntimeException("No snapshot found for this period."));
    }

    @Transactional
    public PayrollMonthlySnapshot updateSnapshotStatus(String id, PayrollStatus newStatus) {
        PayrollMonthlySnapshot existing = getSnapshotById(id);
        existing.setStatus(newStatus);
        return repository.save(existing);
    }
}