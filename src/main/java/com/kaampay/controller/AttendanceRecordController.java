package com.kaampay.controller;

import com.kaampay.entity.AttendanceRecord;
import com.kaampay.entity.AttendanceStatus;
import com.kaampay.service.AttendanceRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceRecordController {

    private final AttendanceRecordService service;

    public AttendanceRecordController(AttendanceRecordService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<AttendanceRecord> createAttendanceRecord(@RequestBody AttendanceRecord record) {
        AttendanceRecord created = service.createAttendanceRecord(record);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords() {
        return ResponseEntity.ok(service.getAllAttendanceRecords());
    }

    @GetMapping("/get/{attendanceId}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecordById(@PathVariable String attendanceId) {
        return ResponseEntity.ok(service.getAttendanceRecordById(attendanceId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getRecordsByCompanyId(companyId));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(service.getRecordsByEmployeeId(employeeId));
    }

    @GetMapping("/getByBranch/{branchId}")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByBranchId(@PathVariable String branchId) {
        return ResponseEntity.ok(service.getRecordsByBranchId(branchId));
    }

    @GetMapping("/getByCompanyAndDate")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByCompanyAndDate(
            @RequestParam String companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getRecordsByCompanyAndDate(companyId, date));
    }

    @GetMapping("/getByEmployeeAndDate")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByEmployeeAndDate(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getRecordsByEmployeeAndDate(employeeId, date));
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByStatus(@PathVariable AttendanceStatus status) {
        return ResponseEntity.ok(service.getRecordsByStatus(status));
    }

    @GetMapping("/getByCompanyAndDateRange")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByCompanyAndDateRange(
            @RequestParam String companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getRecordsByCompanyAndDateRange(companyId, startDate, endDate));
    }

    @GetMapping("/getByEmployeeAndDateRange")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByEmployeeAndDateRange(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getRecordsByEmployeeAndDateRange(employeeId, startDate, endDate));
    }

    // ==================== UPDATE (PATCH) ====================
    @PatchMapping("/update/{attendanceId}")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(
            @PathVariable String attendanceId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateAttendanceRecord(attendanceId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{attendanceId}")
    public ResponseEntity<Map<String, String>> deleteAttendanceRecord(@PathVariable String attendanceId) {
        service.deleteAttendanceRecord(attendanceId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Attendance record deleted successfully");
        response.put("attendanceId", attendanceId);
        return ResponseEntity.ok(response);
    }
}