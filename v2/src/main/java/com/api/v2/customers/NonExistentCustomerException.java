package com.api.v2.customers;

public class NonExistentCustomerException extends RuntimeException {
    public NonExistentCustomerException(String ssn) {
        super("The sought customer whose SSN is %s was not found.".formatted(ssn));
    }
}
