package com.api.v2.people.domain;

import com.api.v2.people.domain.exposed.Person;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    @Query("{ 'ssn': ?0, 'email': ?1 }")
    Mono<Person> find(String ssn, String email);

}
