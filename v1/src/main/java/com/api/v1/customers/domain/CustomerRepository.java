package com.api.v1.customers.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'sin': ?0 }")
    Optional<Customer> findBySin(String sin);

    @Query("{ 'email': ?0 }")
    Optional<Customer> findByEmail(String email);
}
