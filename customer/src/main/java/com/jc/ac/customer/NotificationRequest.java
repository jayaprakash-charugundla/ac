package com.jc.ac.customer;

public record NotificationRequest(
    Integer toCustomerId,
    String toCustomerName,
    String message
) {
}
