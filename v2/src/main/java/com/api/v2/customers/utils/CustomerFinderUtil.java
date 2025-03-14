package com.api.v2.customers.utils;

import com.api.v2.customers.exceptions.NonExistentCustomerException;
import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.domain.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerFinderUtil {

    private final CustomerRepository customerRepository;

    public CustomerFinderUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findById(String id) {
        return customerRepository
                .findAll()
                .filter(c -> c.getId().equals(id))
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new NonExistentCustomerException(id)));
    }
}
