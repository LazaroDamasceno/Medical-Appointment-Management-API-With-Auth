package com.api.v1.customers.utils;

import com.api.v1.common.*;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.exceptions.CustomerNotFoundException;
import com.api.v1.customers.utils.exposed.CustomerFinder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class CustomerFinderImpl implements CustomerFinder {

    private final CustomerCrudRepository repository;

    public CustomerFinderImpl(CustomerCrudRepository repository) {
        this.repository = repository;
    }

    public Customer findById(@ObjectId String id) {
        Optional<Customer> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        return optional.get();
    }
}
