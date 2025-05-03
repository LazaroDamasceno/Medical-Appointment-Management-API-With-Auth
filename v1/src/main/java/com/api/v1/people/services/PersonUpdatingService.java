package com.api.v1.people.services;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import reactor.core.publisher.Mono;

public interface PersonUpdatingService {
    Mono<Person> update(Person oldPerson, PersonUpdatingDto updatingDto);
}
