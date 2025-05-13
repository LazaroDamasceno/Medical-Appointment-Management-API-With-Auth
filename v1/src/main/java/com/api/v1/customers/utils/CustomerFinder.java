package com.api.v1.customers.utils;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerFinder {

    private final CustomerRepository repository;

    public Optional<Customer> findOptionalById(@ObjectId String id) {
        return repository.findById(id);
    }
}
