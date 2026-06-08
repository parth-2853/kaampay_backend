package com.kaampay.service;

import com.kaampay.entity.LeaveBalance;
import com.kaampay.repository.LeaveBalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository repository;

    public LeaveBalanceService(LeaveBalanceRepository repository) {
        this.repository = repository;
    }

    public LeaveBalance createBalance(LeaveBalance balance) {
        return repository.save(balance);
    }

    public List<LeaveBalance> getAllBalances() {
        return repository.findAll();
    }

    public LeaveBalance getBalanceById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Balance not found with id: " + id));
    }

    public List<LeaveBalance> getBalancesByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<LeaveBalance> getEmployeeBalancesForYear(String employeeId, Integer year) {
        return repository.findByEmployeeIdAndYear(employeeId, year);
    }

    @Transactional
    public LeaveBalance updateBalance(String id, Map<String, Object> updates) {
        LeaveBalance existingBalance = getBalanceById(id);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "usedDays":
                        existingBalance.setUsedDays(Integer.parseInt(value.toString()));
                        break;
                    case "remainingDays":
                        existingBalance.setRemainingDays(Integer.parseInt(value.toString()));
                        break;
                    case "carriedOver":
                        existingBalance.setCarriedOver(Integer.parseInt(value.toString()));
                        break;
                    case "totalDays":
                        existingBalance.setTotalDays(Integer.parseInt(value.toString()));
                        break;
                    default:
                        break;
                }
            }
        });

        return repository.save(existingBalance);
    }

    public void deleteBalance(String id) {
        LeaveBalance balance = getBalanceById(id);
        repository.delete(balance);
    }
}