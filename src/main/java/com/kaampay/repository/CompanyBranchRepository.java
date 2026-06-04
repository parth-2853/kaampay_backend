package com.kaampay.repository;

import com.kaampay.entity.CompanyBranch;
import com.kaampay.entity.BranchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, String> {

    List<CompanyBranch> findByCompanyId(String companyId);

    Optional<CompanyBranch> findByBranchCode(String branchCode);

    List<CompanyBranch> findByPhone(String phone);

    List<CompanyBranch> findByStatus(BranchStatus status);

    List<CompanyBranch> findByCompanyIdAndIsMainBranchTrue(String companyId);

    boolean existsByBranchCode(String branchCode);
}