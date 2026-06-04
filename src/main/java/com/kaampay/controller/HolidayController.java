package com.kaampay.controller;

import com.kaampay.entity.Holiday;
import com.kaampay.service.HolidayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    private final HolidayService service;

    public HolidayController(HolidayService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping("/create")
    public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
        Holiday created = service.createHoliday(holiday);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/create/batch")
    public ResponseEntity<List<Holiday>> createMultipleHolidays(@RequestBody List<Holiday> holidays) {
        List<Holiday> created = service.createMultipleHolidays(holidays);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ==================== READ (GET) ====================
    @GetMapping("/getAll")
    public ResponseEntity<List<Holiday>> getAllHolidays() {
        return ResponseEntity.ok(service.getAllHolidays());
    }

    @GetMapping("/get/{holidayId}")
    public ResponseEntity<Holiday> getHolidayById(@PathVariable String holidayId) {
        return ResponseEntity.ok(service.getHolidayById(holidayId));
    }

    @GetMapping("/getByCompany/{companyId}")
    public ResponseEntity<List<Holiday>> getHolidaysByCompanyId(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getHolidaysByCompanyId(companyId));
    }

    @GetMapping("/getByName/{holidayName}")
    public ResponseEntity<List<Holiday>> getHolidaysByName(@PathVariable String holidayName) {
        return ResponseEntity.ok(service.getHolidaysByName(holidayName));
    }

    @GetMapping("/getUpcoming/{companyId}")
    public ResponseEntity<List<Holiday>> getUpcomingHolidays(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getUpcomingHolidays(companyId));
    }

    @GetMapping("/getActive/{companyId}")
    public ResponseEntity<List<Holiday>> getActiveHolidays(@PathVariable String companyId) {
        return ResponseEntity.ok(service.getActiveHolidays(companyId));
    }

    @GetMapping("/getByDateRange")
    public ResponseEntity<List<Holiday>> getHolidaysByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getHolidaysByDateRange(startDate, endDate));
    }

    @GetMapping("/getByYear/{companyId}/{year}")
    public ResponseEntity<List<Holiday>> getHolidaysByYear(
            @PathVariable String companyId,
            @PathVariable int year) {
        return ResponseEntity.ok(service.getHolidaysByYear(companyId, year));
    }

    @GetMapping("/checkHoliday/{companyId}/{date}")
    public ResponseEntity<Map<String, Object>> checkHolidayOnDate(
            @PathVariable String companyId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        boolean isHoliday = service.isHolidayOnDate(companyId, date);
        Map<String, Object> response = new HashMap<>();
        response.put("date", date);
        response.put("isHoliday", isHoliday);
        response.put("companyId", companyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOverlapping")
    public ResponseEntity<List<Holiday>> getOverlappingHolidays(
            @RequestParam String companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(service.getOverlappingHolidays(companyId, startDate, endDate));
    }

    // ==================== READ - is_event specific endpoints ====================

    @GetMapping("/getEvents")
    public ResponseEntity<List<Holiday>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    @GetMapping("/getHolidaysOnly")
    public ResponseEntity<List<Holiday>> getAllHolidaysOnly() {
        return ResponseEntity.ok(service.getAllHolidaysOnly());
    }

    @GetMapping("/getByIsEvent/{isEvent}")
    public ResponseEntity<List<Holiday>> getHolidaysByIsEvent(@PathVariable String isEvent) {
        return ResponseEntity.ok(service.getHolidaysByIsEvent(isEvent));
    }

    @GetMapping("/getByCompanyAndIsEvent/{companyId}/{isEvent}")
    public ResponseEntity<List<Holiday>> getHolidaysByCompanyAndIsEvent(
            @PathVariable String companyId,
            @PathVariable String isEvent) {
        return ResponseEntity.ok(service.getHolidaysByCompanyAndIsEvent(companyId, isEvent));
    }

    @GetMapping("/getByYearAndIsEvent/{companyId}/{year}/{isEvent}")
    public ResponseEntity<List<Holiday>> getHolidaysByYearAndIsEvent(
            @PathVariable String companyId,
            @PathVariable int year,
            @PathVariable String isEvent) {
        return ResponseEntity.ok(service.getHolidaysByYearAndIsEvent(companyId, year, isEvent));
    }

    // ==================== UPDATE (PATCH) ====================
    @PatchMapping("/update/{holidayId}")
    public ResponseEntity<Holiday> updateHoliday(
            @PathVariable String holidayId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.updateHoliday(holidayId, updates));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/delete/{holidayId}")
    public ResponseEntity<Map<String, String>> deleteHoliday(@PathVariable String holidayId) {
        service.deleteHoliday(holidayId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Holiday deleted successfully");
        response.put("holidayId", holidayId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteByCompany/{companyId}")
    public ResponseEntity<Map<String, String>> deleteAllHolidaysByCompanyId(@PathVariable String companyId) {
        service.deleteAllHolidaysByCompanyId(companyId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "All holidays for company deleted successfully");
        response.put("companyId", companyId);
        return ResponseEntity.ok(response);
    }
}