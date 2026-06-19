package com.kaampay.controller;

import com.kaampay.entity.OvertimeRecord;
import com.kaampay.service.OvertimeRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/overtime")
public class OvertimeRecordController {

    private final OvertimeRecordService service;

    public OvertimeRecordController(OvertimeRecordService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<OvertimeRecord> createOvertime(@RequestBody OvertimeRecord record) {
        return new ResponseEntity<>(service.createOvertime(record), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OvertimeRecord>> getAllOvertime() {
        return ResponseEntity.ok(service.getAllOvertime());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OvertimeRecord> getOvertimeById(@PathVariable String id) {
        return ResponseEntity.ok(service.getOvertimeById(id));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    public ResponseEntity<List<OvertimeRecord>> getOvertimeByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getOvertimeByEmployeeId(employeeId));
    }

    @GetMapping("/getByEmployeeAndDateRange")
    public ResponseEntity<List<OvertimeRecord>> getOvertimeByDateRange(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getOvertimeByDateRange(employeeId, startDate, endDate));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<OvertimeRecord> updateOvertime(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateOvertime(id, updates));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteOvertime(@PathVariable String id) {
        service.deleteOvertime(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Overtime record deleted successfully");
        response.put("overtimeId", id);
        return ResponseEntity.ok(response);
    }
}