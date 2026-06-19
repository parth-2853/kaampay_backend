package com.kaampay.service;

import com.kaampay.entity.AttendanceRecord;
import com.kaampay.entity.AttendanceStatus;
import com.kaampay.repository.AttendanceRecordRepository;
import com.kaampay.repository.EmployeeRepository;
import com.kaampay.repository.ShiftDayRepository;
import com.kaampay.repository.ShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.kaampay.entity.Employee;
import com.kaampay.entity.Shift;
import com.kaampay.entity.ShiftDay;
@Service
public class AttendanceRecordService {

    private final AttendanceRecordRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftDayRepository shiftDayRepository;

    // Injecting all required repositories
    public AttendanceRecordService(
            AttendanceRecordRepository repository,
            EmployeeRepository employeeRepository,
            ShiftRepository shiftRepository,
            ShiftDayRepository shiftDayRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
        this.shiftDayRepository = shiftDayRepository;
    }

// ==================== CREATE ====================
    @Transactional
    public AttendanceRecord createAttendanceRecord(AttendanceRecord record) {
        if (record.getAttendanceId() == null || record.getAttendanceId().isEmpty()) {
            record.setAttendanceId(UUID.randomUUID().toString());
        }

        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        LocalDateTime currentIstTime = LocalDateTime.now(istZone);

        // 1. Identify if this is a manual override by the owner
        // We must check this BEFORE we auto-fill the current time
        boolean isManualOverride = record.getPunchInTime() != null;

        // 2. Set Dates and Times if missing
        if (record.getAttendanceDate() == null) {
            record.setAttendanceDate(currentIstTime.toLocalDate());
        }
        if (!isManualOverride) {
            record.setPunchInTime(currentIstTime);
        }

        // Prevent duplicate records for the same employee on the same date
        if (record.getEmployeeId() != null && record.getAttendanceDate() != null) {
            Optional<AttendanceRecord> existingRecord = repository.findByEmployeeIdAndAttendanceDateAndCompanyId(
                    record.getEmployeeId(), record.getAttendanceDate(), record.getCompanyId());
            if (existingRecord.isPresent()) {
                throw new RuntimeException("Attendance record already exists for this employee on the given date.");
            }
        }

        // 3. Shift and Late Calculation
        if (record.getEmployeeId() != null) {
            Optional<Employee> employeeOpt = employeeRepository.findById(record.getEmployeeId());

            if (employeeOpt.isPresent() && employeeOpt.get().getShiftId() != null) {
                String shiftId = employeeOpt.get().getShiftId();
                Optional<Shift> shiftOpt = shiftRepository.findById(shiftId);

                if (shiftOpt.isPresent()) {
                    Shift shift = shiftOpt.get();
                    String dayName = record.getAttendanceDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                    Optional<ShiftDay> shiftDayOpt = shiftDayRepository.findByShiftIdAndDayName(shiftId, dayName);

                    // A valid working day means the row exists, isActive is true, and it has a start time.
                    boolean hasActiveWorkingDay = shiftDayOpt.isPresent() &&
                            Boolean.TRUE.equals(shiftDayOpt.get().getIsActive()) &&
                            shiftDayOpt.get().getStartTime() != null;

                    if (hasActiveWorkingDay) {
                        // Employee has a shift today -> Calculate lateness
                        ShiftDay shiftDay = shiftDayOpt.get();
                        LocalTime expectedStartTime = shiftDay.getStartTime();
                        int lateAllowance = shift.getLateAllowanceMinutes() != null ? shift.getLateAllowanceMinutes() : 0;
                        LocalTime actualPunchIn = record.getPunchInTime().toLocalTime();

                        LocalTime allowedStartTime = expectedStartTime.plusMinutes(lateAllowance);

                        if (actualPunchIn.isAfter(allowedStartTime)) {
                            record.setIsLate(true);
                            long minutesLate = Duration.between(expectedStartTime, actualPunchIn).toMinutes();
                            record.setLateMinutes((int) minutesLate);
                        } else {
                            record.setIsLate(false);
                            record.setLateMinutes(0);
                            record.setStatus(AttendanceStatus.present);
                        }
                    } else {
                        // Employee DOES NOT have a shift today
                        if (!isManualOverride) {
                            // It's an auto-punch-in, so reject it immediately
                            throw new RuntimeException("Punch-in rejected: You have no assigned working day today.");
                        } else {
                            // Owner is manually overriding the off-day, so allow it without marking them late
                            record.setIsLate(false);
                            record.setLateMinutes(0);
                            record.setStatus(AttendanceStatus.present);
                        }
                    }
                }
            }
        }

        return repository.save(record);
    }

    // ==================== READ ====================
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return repository.findAll();
    }

    public AttendanceRecord getAttendanceRecordById(String attendanceId) {
        return repository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + attendanceId));
    }

    public List<AttendanceRecord> getRecordsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    public List<AttendanceRecord> getRecordsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<AttendanceRecord> getRecordsByBranchId(String branchId) {
        return repository.findByBranchId(branchId);
    }

    public List<AttendanceRecord> getRecordsByCompanyAndDate(String companyId, LocalDate date) {
        return repository.findByCompanyIdAndAttendanceDate(companyId, date);
    }

    public List<AttendanceRecord> getRecordsByEmployeeAndDate(String employeeId, LocalDate date) {
        return repository.findByEmployeeIdAndAttendanceDate(employeeId, date);
    }

    public List<AttendanceRecord> getRecordsByStatus(AttendanceStatus status) {
        return repository.findByStatus(status);
    }

    public List<AttendanceRecord> getRecordsByCompanyAndDateRange(String companyId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date.");
        }
        return repository.findByCompanyIdAndAttendanceDateBetween(companyId, startDate, endDate);
    }

    public List<AttendanceRecord> getRecordsByEmployeeAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date.");
        }
        return repository.findByEmployeeIdAndAttendanceDateBetween(employeeId, startDate, endDate);
    }

    // ==================== UPDATE (PATCH) ====================
    @Transactional
    public AttendanceRecord updateAttendanceRecord(String attendanceId, Map<String, Object> updates) {
        AttendanceRecord existingRecord = getAttendanceRecordById(attendanceId);

        boolean autoPunchOutTriggered = false;
        if ((updates.containsKey("punchOutLocation") || updates.containsKey("punchOutLatitude"))
                && !updates.containsKey("punchOutTime")) {

            ZoneId istZone = ZoneId.of("Asia/Kolkata");
            existingRecord.setPunchOutTime(LocalDateTime.now(istZone));
            autoPunchOutTriggered = true;
        }

        // --- 2. APPLY MANUAL UPDATES ---
        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "branchId": existingRecord.setBranchId((String) value); break;
                    case "attendanceDate": existingRecord.setAttendanceDate(LocalDate.parse((String) value)); break;
                    case "punchInTime": existingRecord.setPunchInTime(LocalDateTime.parse((String) value)); break;
                    case "punchOutTime": existingRecord.setPunchOutTime(LocalDateTime.parse((String) value)); break;
                    case "punchInImage": existingRecord.setPunchInImage((String) value); break;
                    case "punchOutImage": existingRecord.setPunchOutImage((String) value); break;
                    case "punchInLocation": existingRecord.setPunchInLocation((String) value); break;
                    case "punchOutLocation": existingRecord.setPunchOutLocation((String) value); break;
                    case "punchInLatitude": existingRecord.setPunchInLatitude(new BigDecimal(value.toString())); break;
                    case "punchInLongitude": existingRecord.setPunchInLongitude(new BigDecimal(value.toString())); break;
                    case "punchOutLatitude": existingRecord.setPunchOutLatitude(new BigDecimal(value.toString())); break;
                    case "punchOutLongitude": existingRecord.setPunchOutLongitude(new BigDecimal(value.toString())); break;
                    case "isLate": existingRecord.setIsLate((Boolean) value); break;
                    case "lateMinutes": existingRecord.setLateMinutes((Integer) value); break;
                    case "totalWorkHours": existingRecord.setTotalWorkHours(new BigDecimal(value.toString())); break;
                    case "overtimeHours": existingRecord.setOvertimeHours(new BigDecimal(value.toString())); break;
                    case "status": existingRecord.setStatus(AttendanceStatus.valueOf((String) value)); break;
                    case "updatedBy": existingRecord.setUpdatedBy((String) value); break;
                    default: break;
                }
            }
        });
        if (updates.containsKey("punchInTime") && !updates.containsKey("isLate") && existingRecord.getEmployeeId() != null) {
            employeeRepository.findById(existingRecord.getEmployeeId()).ifPresent(employee -> {
                String shiftId = employee.getShiftId();
                if (shiftId != null) {
                    shiftRepository.findById(shiftId).ifPresent(shift -> {
                        LocalDate dateToCheck = existingRecord.getAttendanceDate() != null ? existingRecord.getAttendanceDate() : LocalDate.now();
                        String dayName = dateToCheck.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                        shiftDayRepository.findByShiftIdAndDayName(shiftId, dayName).ifPresent(shiftDay -> {
                            if (Boolean.TRUE.equals(shiftDay.getIsActive()) && shiftDay.getStartTime() != null) {
                                LocalTime expectedStartTime = shiftDay.getStartTime();
                                int lateAllowance = shift.getLateAllowanceMinutes() != null ? shift.getLateAllowanceMinutes() : 0;
                                LocalTime actualPunchIn = existingRecord.getPunchInTime().toLocalTime();
                                LocalTime allowedStartTime = expectedStartTime.plusMinutes(lateAllowance);

                                if (actualPunchIn.isAfter(allowedStartTime)) {
                                    existingRecord.setIsLate(true);
                                    long minutesLate = java.time.Duration.between(expectedStartTime, actualPunchIn).toMinutes();
                                    existingRecord.setLateMinutes((int) minutesLate);
                                } else {
                                    existingRecord.setIsLate(false);
                                    existingRecord.setLateMinutes(0);
                                }
                            }
                        });
                    });
                }
            });
        }

        if (existingRecord.getPunchInTime() != null && existingRecord.getPunchOutTime() != null) {
            if ((updates.containsKey("punchInTime") || updates.containsKey("punchOutTime") || autoPunchOutTriggered)
                    && !updates.containsKey("totalWorkHours")) {

                long minutesWorked = java.time.Duration.between(existingRecord.getPunchInTime(), existingRecord.getPunchOutTime()).toMinutes();

                if (minutesWorked < 0) minutesWorked = 0;

                BigDecimal hoursWorked = BigDecimal.valueOf(minutesWorked)
                        .divide(BigDecimal.valueOf(60), 2, java.math.RoundingMode.HALF_UP);
                existingRecord.setTotalWorkHours(hoursWorked);
            }
        }

        return repository.save(existingRecord);
    }

    // ==================== DELETE ====================
    @Transactional
    public void deleteAttendanceRecord(String attendanceId) {
        AttendanceRecord record = getAttendanceRecordById(attendanceId);
        repository.delete(record);
    }
}