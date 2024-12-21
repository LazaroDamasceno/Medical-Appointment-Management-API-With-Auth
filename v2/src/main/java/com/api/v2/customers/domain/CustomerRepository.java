package com.api.v2.customers.domain;

import com.api.v2.people.domain.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, ObjectId> {

    @Query("{ 'person': ?0 }")
    Mono<Customer> findByPerson(Person person);
}
