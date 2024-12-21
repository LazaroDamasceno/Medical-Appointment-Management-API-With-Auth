package com.api.v2.customers.utils;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.people.utils.PersonFinderUtil;
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
                .flatMap(customerRepository::findByPerson);
    }
}
