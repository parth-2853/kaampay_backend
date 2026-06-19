package com.kaampay.service;

import com.kaampay.entity.PaymentLedger;
import com.kaampay.entity.PaymentType;
import com.kaampay.repository.PaymentLedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentLedgerService {

    private final PaymentLedgerRepository repository;

    public PaymentLedgerService(PaymentLedgerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PaymentLedger createPayment(PaymentLedger payment) {
        if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
            payment.setPaymentId(UUID.randomUUID().toString());
        }
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }
        return repository.save(payment);
    }

    public List<PaymentLedger> getAllPayments() {
        return repository.findAll();
    }

    public PaymentLedger getPaymentById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public List<PaymentLedger> getPaymentsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Transactional
    public PaymentLedger updatePayment(String id, Map<String, Object> updates) {
        PaymentLedger existing = getPaymentById(id);

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "paymentType": existing.setPaymentType(PaymentType.valueOf((String) value)); break;
                    case "paymentMethod": existing.setPaymentMethod((String) value); break;
                    case "paymentDate": existing.setPaymentDate(LocalDate.parse((String) value)); break;
                    case "amount": existing.setAmount(new BigDecimal(value.toString())); break;
                    case "note": existing.setNote((String) value); break;
                }
            }
        });
        return repository.save(existing);
    }

    @Transactional
    public void deletePayment(String id) {
        repository.deleteById(id);
    }
}