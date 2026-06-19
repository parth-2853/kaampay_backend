package com.kaampay.service;

import com.kaampay.entity.OvertimeRecord;
import com.kaampay.repository.OvertimeRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OvertimeRecordService {

    private final OvertimeRecordRepository repository;

    public OvertimeRecordService(OvertimeRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OvertimeRecord createOvertime(OvertimeRecord record) {
        if (record.getOvertimeId() == null || record.getOvertimeId().isEmpty()) {
            record.setOvertimeId(UUID.randomUUID().toString());
        }
        if (record.getOvertimeDate() == null) {
            record.setOvertimeDate(LocalDate.now());
        }
        // Optional Backend Math Verification (since Flutter sends total_amount, we can trust it,
        // but it's good practice to ensure hours * rate = total if needed)
        return repository.save(record);
    }

    public List<OvertimeRecord> getAllOvertime() {
        return repository.findAll();
    }

    public OvertimeRecord getOvertimeById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Overtime record not found with id: " + id));
    }

    public List<OvertimeRecord> getOvertimeByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<OvertimeRecord> getOvertimeByDateRange(String employeeId, LocalDate startDate, LocalDate endDate) {
        return repository.findByEmployeeIdAndOvertimeDateBetween(employeeId, startDate, endDate);
    }

    @Transactional
    public OvertimeRecord updateOvertime(String id, Map<String, Object> updates) {
        OvertimeRecord existing = getOvertimeById(id);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "overtimeDate": existing.setOvertimeDate(LocalDate.parse((String) value)); break;
                    case "hours": existing.setHours(new BigDecimal(value.toString())); break;
                    case "ratePerHour": existing.setRatePerHour(new BigDecimal(value.toString())); break;
                    case "totalAmount": existing.setTotalAmount(new BigDecimal(value.toString())); break;
                    case "note": existing.setNote((String) value); break;
                }
            }
        });
        return repository.save(existing);
    }

    @Transactional
    public void deleteOvertime(String id) {
        repository.deleteById(id);
    }
}