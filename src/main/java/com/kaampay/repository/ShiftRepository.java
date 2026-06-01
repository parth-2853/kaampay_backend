package com.kaampay.repository;

import com.kaampay.entity.Shift;
import com.kaampay.entity.ShiftStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, String> {
    List<Shift> findByCompanyId(String companyId);
    List<Shift> findByCompanyIdAndStatus(String companyId, ShiftStatus status);
}