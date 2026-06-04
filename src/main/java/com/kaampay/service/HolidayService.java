package com.kaampay.service;

import com.kaampay.entity.Holiday;
import com.kaampay.repository.HolidayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HolidayService {

    private final HolidayRepository repository;
    private final CompanyService companyService; // To validate company exists

    public HolidayService(HolidayRepository repository, CompanyService companyService) {
        this.repository = repository;
        this.companyService = companyService;
    }

    // CREATE
    @Transactional
    public Holiday createHoliday(Holiday holiday) {
        // Validate company exists
        companyService.getCompanyById(holiday.getCompanyId());

        // Validate is_event value
        validateIsEvent(holiday.getIsEvent());

        // Validate holiday dates
        validateHolidayDates(holiday);

        // Check for overlapping holidays
        checkForOverlappingHolidays(holiday);

        if (holiday.getHolidayId() == null || holiday.getHolidayId().isEmpty()) {
            holiday.setHolidayId(UUID.randomUUID().toString());
        }

        // Set default is_event if null
        if (holiday.getIsEvent() == null || holiday.getIsEvent().isEmpty()) {
            holiday.setIsEvent("false");
        }

        return repository.save(holiday);
    }

    // CREATE multiple holidays at once
    @Transactional
    public List<Holiday> createMultipleHolidays(List<Holiday> holidays) {
        return holidays.stream()
                .map(this::createHoliday)
                .toList();
    }

    // READ - Get all holidays
    public List<Holiday> getAllHolidays() {
        return repository.findAll();
    }

    // READ - Get by ID
    public Holiday getHolidayById(String holidayId) {
        return repository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found with id: " + holidayId));
    }

    // READ - Get by Company ID
    public List<Holiday> getHolidaysByCompanyId(String companyId) {
        // Validate company exists
        companyService.getCompanyById(companyId);
        return repository.findByCompanyIdOrderByHolidayStartDateAsc(companyId);
    }

    // READ - Get by Holiday Name (partial match)
    public List<Holiday> getHolidaysByName(String holidayName) {
        List<Holiday> holidays = repository.findByHolidayNameContainingIgnoreCase(holidayName);
        if (holidays.isEmpty()) {
            throw new RuntimeException("No holidays found with name containing: " + holidayName);
        }
        return holidays;
    }

    // READ - Get upcoming holidays for a company
    public List<Holiday> getUpcomingHolidays(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findByCompanyIdAndHolidayStartDateGreaterThanEqualOrderByHolidayStartDateAsc(companyId, LocalDate.now());
    }

    // READ - Get active/today's holidays for a company
    public List<Holiday> getActiveHolidays(String companyId) {
        companyService.getCompanyById(companyId);
        return repository.findActiveHolidays(companyId, LocalDate.now());
    }

    // READ - Get holidays by date range
    public List<Holiday> getHolidaysByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date");
        }
        return repository.findByHolidayStartDateBetween(startDate, endDate);
    }

    // READ - Get holidays by year for a company
    public List<Holiday> getHolidaysByYear(String companyId, int year) {
        companyService.getCompanyById(companyId);
        return repository.findHolidaysByYear(companyId, year);
    }

    // READ - Check if a specific date is a holiday
    public boolean isHolidayOnDate(String companyId, LocalDate date) {
        return repository.isHolidayOnDate(companyId, date);
    }

    // READ - Get overlapping holidays (for validation)
    public List<Holiday> getOverlappingHolidays(String companyId, LocalDate startDate, LocalDate endDate) {
        return repository.findOverlappingHolidays(companyId, startDate, endDate);
    }

    // READ - Get events only (is_event = "true")
    public List<Holiday> getAllEvents() {
        return repository.findAllEvents();
    }

    // READ - Get holidays only (is_event = "false")
    public List<Holiday> getAllHolidaysOnly() {
        return repository.findAllHolidaysOnly();
    }

    // READ - Get by is_event status
    public List<Holiday> getHolidaysByIsEvent(String isEvent) {
        validateIsEvent(isEvent);
        return repository.findByIsEvent(isEvent);
    }

    // READ - Get by company ID and is_event
    public List<Holiday> getHolidaysByCompanyAndIsEvent(String companyId, String isEvent) {
        companyService.getCompanyById(companyId);
        validateIsEvent(isEvent);
        return repository.findByCompanyIdAndIsEvent(companyId, isEvent);
    }

    // READ - Get holidays by year and is_event for a company
    public List<Holiday> getHolidaysByYearAndIsEvent(String companyId, int year, String isEvent) {
        companyService.getCompanyById(companyId);
        validateIsEvent(isEvent);
        return repository.findHolidaysByYearAndIsEvent(companyId, year, isEvent);
    }

    // UPDATE - Partial update
    @Transactional
    public Holiday updateHoliday(String holidayId, Map<String, Object> updates) {
        Holiday existingHoliday = getHolidayById(holidayId);

        // Store original dates for overlap validation
        LocalDate originalStartDate = existingHoliday.getHolidayStartDate();
        LocalDate originalEndDate = existingHoliday.getHolidayEndDate();
        String originalCompanyId = existingHoliday.getCompanyId();

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "companyId":
                        // Validate new company exists
                        companyService.getCompanyById((String) value);
                        existingHoliday.setCompanyId((String) value);
                        break;
                    case "holidayName":
                        existingHoliday.setHolidayName((String) value);
                        break;
                    case "holidayStartDate":
                        existingHoliday.setHolidayStartDate(LocalDate.parse(value.toString()));
                        break;
                    case "holidayEndDate":
                        existingHoliday.setHolidayEndDate(LocalDate.parse(value.toString()));
                        break;
                    case "isEvent":
                        String eventValue = (String) value;
                        validateIsEvent(eventValue);
                        existingHoliday.setIsEvent(eventValue);
                        break;
                    default:
                        // Unknown field, ignore
                        break;
                }
            }
        });

        // Validate updated dates
        validateHolidayDates(existingHoliday);

        // Check for overlapping holidays (excluding current holiday)
        String companyId = existingHoliday.getCompanyId();
        LocalDate newStartDate = existingHoliday.getHolidayStartDate();
        LocalDate newEndDate = existingHoliday.getHolidayEndDate();

        // Only check overlap if dates or company changed
        if (!companyId.equals(originalCompanyId) ||
                !newStartDate.equals(originalStartDate) ||
                !newEndDate.equals(originalEndDate)) {

            List<Holiday> overlapping = repository.findOverlappingHolidays(companyId, newStartDate, newEndDate);
            overlapping.stream()
                    .filter(h -> !h.getHolidayId().equals(holidayId))
                    .findFirst()
                    .ifPresent(h -> {
                        throw new RuntimeException("Holiday overlaps with existing holiday: " + h.getHolidayName());
                    });
        }

        return repository.save(existingHoliday);
    }

    // DELETE - Hard delete
    @Transactional
    public void deleteHoliday(String holidayId) {
        Holiday holiday = getHolidayById(holidayId);
        repository.delete(holiday);
    }

    // DELETE - Delete all holidays for a company
    @Transactional
    public void deleteAllHolidaysByCompanyId(String companyId) {
        companyService.getCompanyById(companyId);
        repository.deleteByCompanyId(companyId);
    }

    // Helper methods
    private void validateHolidayDates(Holiday holiday) {
        if (holiday.getHolidayStartDate() == null || holiday.getHolidayEndDate() == null) {
            throw new RuntimeException("Holiday start date and end date are required");
        }

        if (holiday.getHolidayStartDate().isAfter(holiday.getHolidayEndDate())) {
            throw new RuntimeException("Holiday start date cannot be after end date");
        }

        if (holiday.getHolidayStartDate().isBefore(LocalDate.now().minusYears(1))) {
            throw new RuntimeException("Cannot create holiday more than 1 year in the past");
        }
    }

    private void checkForOverlappingHolidays(Holiday holiday) {
        List<Holiday> overlapping = repository.findOverlappingHolidays(
                holiday.getCompanyId(),
                holiday.getHolidayStartDate(),
                holiday.getHolidayEndDate()
        );

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Holiday overlaps with existing holiday: " + overlapping.get(0).getHolidayName());
        }
    }

    private void validateIsEvent(String isEvent) {
        if (isEvent != null && !isEvent.isEmpty()) {
            if (!isEvent.equals("true") && !isEvent.equals("false")) {
                throw new RuntimeException("isEvent must be 'true' or 'false'");
            }
        }
    }
}