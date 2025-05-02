package com.api.v2.customers.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Customer whose id is %s was not found".formatted(id));
    }
}
