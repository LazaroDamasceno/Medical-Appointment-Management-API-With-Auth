package com.api.v1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    Optional<String> findBySin(String sin);

    Optional<String> findByEmail(String email);
}
