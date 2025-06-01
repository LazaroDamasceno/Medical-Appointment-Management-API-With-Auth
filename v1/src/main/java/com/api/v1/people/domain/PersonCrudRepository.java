package com.api.v1.people.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonCrudRepository extends MongoRepository<Person, String> {

    @Query("{ $or: [ { 'sin': ?0 }, { 'email': ?1 } ] }")
    Optional<Person> findBySinOrEmail(String sin, String email);
}
