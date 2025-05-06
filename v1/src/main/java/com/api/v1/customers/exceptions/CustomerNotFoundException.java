package com.api.v1.customers.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerId) {
        super("Customer whose id is %s was not found".formatted(customerId));
    }
}
