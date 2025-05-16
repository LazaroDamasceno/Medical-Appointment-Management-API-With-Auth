package com.api.v1.customers.utils;

import com.api.v1.customers.domain.Customer;

import java.util.Optional;

public interface CustomerFinder {
    Optional<Customer> findOptionalById(String id);
}
