package com.api.v1.customers.utils;

import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class CustomerFinder {

    private final CustomerRepository customerRepository;

    public CustomerFinder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findById(String customerId) {
        return customerRepository
                .findById(customerId)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)));
    }

}
