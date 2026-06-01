package com.kaampay.controller;

import com.kaampay.entity.Shift;
import com.kaampay.service.ShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService service;

    public ShiftController(ShiftService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        Shift created = service.createShift(shift);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/get/{shiftId}")
    public ResponseEntity<Shift> getShiftById(@PathVariable String shiftId) {
        return ResponseEntity.ok(service.getShiftById(shiftId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Shift>> getShiftsByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getShiftsByCompanyId(companyId));
    }

    // UPDATE (Flexible PATCH)
    @PatchMapping("/patch/{shiftId}")
    public ResponseEntity<Shift> patchShift(
            @PathVariable String shiftId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.patchShift(shiftId, updates));
    }

    // DELETE
    @DeleteMapping("/delete/{shiftId}")
    public ResponseEntity<Map<String, String>> deleteShift(@PathVariable String shiftId) {
        service.deleteShift(shiftId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Shift deleted successfully");
        response.put("shiftId", shiftId);
        return ResponseEntity.ok(response);
    }
}