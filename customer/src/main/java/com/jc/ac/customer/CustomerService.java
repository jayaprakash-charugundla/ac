package com.jc.ac.customer;

import com.jc.ac.customer.fraud.FraudCheckProvider;
import com.jc.ac.customer.fraud.FraudCheckResponse;
import com.jc.ac.customer.rabbitmq.RabbitMQMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudCheckProvider fraudCheckProvider;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

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

        NotificationRequest notificationRequest = new NotificationRequest(
            customer.getId(),
            customer.getEmail(),
            String.format("Hi %s, welcome to jc...", customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(notificationRequest,"internal.exchange",
            "internal.notification.routing-key");
    }
}
