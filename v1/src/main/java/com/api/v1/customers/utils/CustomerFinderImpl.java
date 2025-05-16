package com.api.v1.customers.utils;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class CustomerFinderImpl implements CustomerFinder {

    private final CustomerRepository repository;

    public CustomerFinderImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer findOptionalById(@ObjectId String id) {
        Optional<Customer> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        return optional.get();
    }
}
