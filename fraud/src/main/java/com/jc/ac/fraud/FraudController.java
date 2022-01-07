package com.jc.ac.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/fraud-check")
public record FraudController(FraudCheckService fraudCheckService) {

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudster = fraudCheckService.isFraudulentCustomer(customerId);
        return new FraudCheckResponse(isFraudster);
    }

}
