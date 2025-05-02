package com.api.v2.customers.domain;

import com.api.v2.customers.domain.exposed.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
