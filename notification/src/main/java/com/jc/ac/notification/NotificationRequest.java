package com.jc.ac.notification;

public record NotificationRequest(
    Integer toCustomerId,
    String toCustomerName,
    String message
) {
}
