package com.api.v2.customers.utils;

import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.domain.exposed.Customer;
import com.api.v2.customers.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerFinder {

    private final CustomerRepository customerRepository;

    public Mono<Customer> findById(String id) {
        return customerRepository
                .findById(id)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        throw new CustomerNotFoundException(id);
                    }
                    return Mono.just(optional.get());
                });
    }

}
