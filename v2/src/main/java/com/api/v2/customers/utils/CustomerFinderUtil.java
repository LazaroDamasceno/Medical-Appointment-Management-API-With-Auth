package com.api.v2.customers.utils;

import com.api.v2.customers.exceptions.NonExistentCustomerException;
import com.api.v2.common.Id;
import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.domain.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerFinderUtil {

    private final CustomerRepository customerRepository;

    public CustomerFinderUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findById(@Id String id) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getId().equals(new ObjectId(id)))
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new NonExistentCustomerException(id)));
    }
}
