package com.api.v1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{ 'sin': ?0 }")
    Optional<String> findBySin(String sin);

    @Query("{ 'email': ?0 }")
    Optional<String> findByEmail(String email);
}
