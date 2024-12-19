package com.api.v2.utils;

import com.api.v2.domain.Customer;
import com.api.v2.domain.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerFinderUtil {

    private final PersonFinderUtil personFinderUtil;
    private final CustomerRepository customerRepository;

    public CustomerFinderUtil(
            PersonFinderUtil personFinderUtil,
            CustomerRepository customerRepository
    ) {
        this.personFinderUtil = personFinderUtil;
        this.customerRepository = customerRepository;
    }

    public Mono<Customer> findBySsn(String ssn) {
        return personFinderUtil
                .findBySsn(ssn)
                .flatMap(person -> customerRepository.findByPerson(person).single());
    }
}
