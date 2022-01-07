package com.jc.ac.fraud;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public record FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {

    public boolean isFraudulentCustomer(Integer customerId) {
        fraudCheckHistoryRepository.save(
            FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build()
        );
        return false;
    }
}
