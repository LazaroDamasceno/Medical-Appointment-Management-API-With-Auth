package com.api.v1.customers.domain;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'sin': ?0 }")
    Optional<Customer> findBySIN(String sin);

    @Query("{ 'email': ?0 }")
    Optional<Customer> findByEmail(String email);
}
