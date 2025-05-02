package com.api.v2.customers.domain;

import com.api.v2.customers.domain.exposed.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    @Query("{ 'person.ssn': ?0 }")
    Mono<Customer> findBySsn(String ssn);

    @Query("{ 'person.email': ?0 }")
    Mono<Customer> findByEmail(String email);

}
