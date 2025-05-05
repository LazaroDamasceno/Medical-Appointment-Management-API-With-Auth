package com.api.v1.customers.utils;

import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public final class CustomerFinder {

    private final CustomerRepository customerRepository;

    public Mono<Customer> findById(String id) {
        return customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)));
    }

}
