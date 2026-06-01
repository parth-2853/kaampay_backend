package com.kaampay.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaampay.entity.Shift;
import com.kaampay.entity.ShiftDay;
import com.kaampay.entity.ShiftFixedBreak;
import com.kaampay.entity.ShiftStatus;
import com.kaampay.repository.ShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ShiftService {

    private final ShiftRepository repository;
    private final ObjectMapper objectMapper;

    public ShiftService(ShiftRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        // Ensure ObjectMapper can handle Java 8 LocalTime parsing
        this.objectMapper = objectMapper.registerModule(new JavaTimeModule());
    }

    // CREATE
    @Transactional
    public Shift createShift(Shift shift) {
        if (shift.getShiftId() == null || shift.getShiftId().isEmpty()) {
            shift.setShiftId(UUID.randomUUID().toString());
        }

        if (shift.getShiftDays() != null) {
            for (ShiftDay day : shift.getShiftDays()) {
                day.setShift(shift);
                if (day.getFixedBreaks() != null) {
                    for (ShiftFixedBreak fb : day.getFixedBreaks()) {
                        fb.setShiftDay(day);
                    }
                }
            }
        }
        return repository.save(shift);
    }

    // READ
    public Shift getShiftById(String shiftId) {
        return repository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with id: " + shiftId));
    }

    public List<Shift> getShiftsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    // FLEXIBLE PARTIAL UPDATE (Super PATCH)
    @Transactional
    public Shift patchShift(String shiftId, Map<String, Object> updates) {
        Shift existingShift = getShiftById(shiftId);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "shiftName":
                        existingShift.setShiftName((String) value);
                        break;
                    case "lateAllowanceMinutes":
                        existingShift.setLateAllowanceMinutes((Integer) value);
                        break;
                    case "status":
                        existingShift.setStatus(ShiftStatus.valueOf((String) value));
                        break;
                    case "shiftDays":
                        // Convert the raw JSON Map array into actual ShiftDay Objects
                        List<ShiftDay> newDays = objectMapper.convertValue(
                                value, new TypeReference<List<ShiftDay>>() {}
                        );

                        // --- THE FIX FOR "Duplicate entry" ERROR ---
                        // 1. Clear the existing days from the managed entity
                        existingShift.getShiftDays().clear();

                        // 2. Force Hibernate to execute the DELETE statements IMMEDIATELY.
                        // This prevents the unique constraint violation by erasing old days first.
                        repository.saveAndFlush(existingShift);

                        // 3. Now wire up and add the new days safely
                        if (newDays != null) {
                            newDays.forEach(day -> {
                                existingShift.addShiftDay(day); // Use the helper method in the Entity
                                if (day.getFixedBreaks() != null) {
                                    day.getFixedBreaks().forEach(fb -> fb.setShiftDay(day));
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        return repository.save(existingShift);
    }
    // DELETE
    @Transactional
    public void deleteShift(String shiftId) {
        Shift shift = getShiftById(shiftId);
        repository.delete(shift);
    }
}