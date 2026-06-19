package com.kaampay.service;

import com.kaampay.entity.EmployeeTransaction;
import com.kaampay.entity.TransactionType;
import com.kaampay.repository.EmployeeTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeTransactionService {

    private final EmployeeTransactionRepository repository;

    public EmployeeTransactionService(EmployeeTransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EmployeeTransaction createTransaction(EmployeeTransaction transaction) {
        if (transaction.getTransactionId() == null || transaction.getTransactionId().isEmpty()) {
            transaction.setTransactionId(UUID.randomUUID().toString());
        }
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDate.now());
        }
        return repository.save(transaction);
    }

    public List<EmployeeTransaction> getAllTransactions() {
        return repository.findAll();
    }

    public EmployeeTransaction getTransactionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public List<EmployeeTransaction> getTransactionsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<EmployeeTransaction> getTransactionsByDateRange(String employeeId, LocalDate startDate, LocalDate endDate) {
        return repository.findByEmployeeIdAndTransactionDateBetween(employeeId, startDate, endDate);
    }

    @Transactional
    public EmployeeTransaction updateTransaction(String id, Map<String, Object> updates) {
        EmployeeTransaction existing = getTransactionById(id);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "transactionType": existing.setTransactionType(TransactionType.valueOf((String) value)); break;
                    case "title": existing.setTitle((String) value); break;
                    case "transactionDate": existing.setTransactionDate(LocalDate.parse((String) value)); break;
                    case "amount": existing.setAmount(new BigDecimal(value.toString())); break;
                    case "note": existing.setNote((String) value); break;
                }
            }
        });
        return repository.save(existing);
    }

    @Transactional
    public void deleteTransaction(String id) {
        repository.deleteById(id);
    }
}