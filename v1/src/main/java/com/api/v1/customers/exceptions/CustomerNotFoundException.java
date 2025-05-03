package com.api.v1.customers.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Customer whose id is %s was not found".formatted(id));
    }
}
