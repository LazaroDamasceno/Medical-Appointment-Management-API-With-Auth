package com.api.v2.people.domain;

import com.api.v2.people.domain.exposed.Person;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}
