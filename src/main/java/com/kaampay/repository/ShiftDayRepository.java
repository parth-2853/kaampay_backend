package com.kaampay.repository;

import com.kaampay.entity.ShiftDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShiftDayRepository extends JpaRepository<ShiftDay, String> {

    // Using a native query looks at the actual MySQL table columns (shift_id, day_name)
    // instead of getting confused by the Java entity variable names.
    @Query(value = "SELECT * FROM shift_days WHERE shift_id = :shiftId AND day_name = :dayName", nativeQuery = true)
    Optional<ShiftDay> findByShiftIdAndDayName(@Param("shiftId") String shiftId, @Param("dayName") String dayName);
}