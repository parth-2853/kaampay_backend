package com.kaampay.repository;

import com.kaampay.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, String> {

    // Find holidays by company ID
    List<Holiday> findByCompanyId(String companyId);

    // Find holidays by company ID ordered by start date
    List<Holiday> findByCompanyIdOrderByHolidayStartDateAsc(String companyId);

    // Find holidays by holiday name (partial match)
    List<Holiday> findByHolidayNameContainingIgnoreCase(String holidayName);

    // Find holidays by is_event
    List<Holiday> findByIsEvent(String isEvent);

    // Find holidays by company ID and is_event
    List<Holiday> findByCompanyIdAndIsEvent(String companyId, String isEvent);

    // Find events only (is_event = "true")
    @Query("SELECT h FROM Holiday h WHERE h.isEvent = 'true'")
    List<Holiday> findAllEvents();

    // Find holidays only (is_event = "false" or null)
    @Query("SELECT h FROM Holiday h WHERE h.isEvent IS NULL OR h.isEvent = 'false'")
    List<Holiday> findAllHolidaysOnly();

    // Find holidays within a date range for a specific company
    @Query("SELECT h FROM Holiday h WHERE h.companyId = :companyId " +
            "AND h.holidayStartDate <= :endDate AND h.holidayEndDate >= :startDate")
    List<Holiday> findOverlappingHolidays(@Param("companyId") String companyId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    // Find upcoming holidays for a company (start date >= today)
    List<Holiday> findByCompanyIdAndHolidayStartDateGreaterThanEqualOrderByHolidayStartDateAsc(String companyId, LocalDate date);

    // Find active holidays for a company (today between start and end date)
    @Query("SELECT h FROM Holiday h WHERE h.companyId = :companyId " +
            "AND h.holidayStartDate <= :today AND h.holidayEndDate >= :today")
    List<Holiday> findActiveHolidays(@Param("companyId") String companyId, @Param("today") LocalDate today);

    // Find holidays by date range (across all companies)
    List<Holiday> findByHolidayStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Check if a holiday exists for a company on a specific date
    @Query("SELECT COUNT(h) > 0 FROM Holiday h WHERE h.companyId = :companyId " +
            "AND h.holidayStartDate <= :date AND h.holidayEndDate >= :date")
    boolean isHolidayOnDate(@Param("companyId") String companyId, @Param("date") LocalDate date);

    // Delete all holidays for a company
    void deleteByCompanyId(String companyId);

    // Find holidays by year for a company
    @Query("SELECT h FROM Holiday h WHERE h.companyId = :companyId " +
            "AND YEAR(h.holidayStartDate) = :year ORDER BY h.holidayStartDate ASC")
    List<Holiday> findHolidaysByYear(@Param("companyId") String companyId, @Param("year") int year);

    // Find holidays by year and is_event for a company
    @Query("SELECT h FROM Holiday h WHERE h.companyId = :companyId " +
            "AND YEAR(h.holidayStartDate) = :year AND h.isEvent = :isEvent ORDER BY h.holidayStartDate ASC")
    List<Holiday> findHolidaysByYearAndIsEvent(@Param("companyId") String companyId,
                                               @Param("year") int year,
                                               @Param("isEvent") String isEvent);
}