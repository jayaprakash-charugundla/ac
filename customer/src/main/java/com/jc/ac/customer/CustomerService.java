package com.jc.ac.customer;

import com.jc.ac.customer.fraud.FraudCheckProvider;
import com.jc.ac.customer.fraud.FraudCheckResponse;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudCheckProvider fraudCheckProvider) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder().firstName(customerRegistrationRequest.firstName())
            .lastName(customerRegistrationRequest.lastName())
            .email(customerRegistrationRequest.email())
            .build();
        customerRepository.save(customer);
        FraudCheckResponse fraudCheckResponse = fraudCheckProvider.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
    }
}
