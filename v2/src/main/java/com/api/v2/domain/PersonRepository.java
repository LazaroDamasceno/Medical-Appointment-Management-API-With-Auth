package com.api.v2.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveMongoRepository<Person, ObjectId> {

    @Query("{ 'ssn': ?0 }")
    Mono<Person> findBySsn(String ssn);
}
