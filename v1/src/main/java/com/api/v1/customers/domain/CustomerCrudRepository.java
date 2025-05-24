package com.api.v1.customers.domain;

import java.util.Optional;

import com.api.v1.customers.domain.exposed.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerCrudRepository extends MongoRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    Optional<Customer> findBySIN(String sin);

    @Query("{ 'person.email': ?0 }")
    Optional<Customer> findByEmail(String email);
}
