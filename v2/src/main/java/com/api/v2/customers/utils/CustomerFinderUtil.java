package com.api.v2.customers.utils;

import com.api.v2.customers.exceptions.NonExistentCustomerException;
import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.people.utils.PersonFinderUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerFinderUtil {

    private final CustomerRepository customerRepository;

    public CustomerFinderUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findBySsn(String ssn) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getSsn().equals(ssn))
                .single();
    }
}
