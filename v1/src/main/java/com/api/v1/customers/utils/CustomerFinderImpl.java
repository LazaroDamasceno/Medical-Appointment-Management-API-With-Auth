package com.api.v1.customers.utils;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class CustomerFinderImpl implements CustomerFinder {

    private final CustomerCrudRepository repository;

    public CustomerFinderImpl(CustomerCrudRepository repository) {
        this.repository = repository;
    }

    public Customer findById(@ObjectId String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
