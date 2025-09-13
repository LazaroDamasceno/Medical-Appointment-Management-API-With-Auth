package com.api.v1.people.repositories;

import com.api.v1.people.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    Optional<String> findBySin(String sin);

    Optional<String> findByEmail(String email);
}
