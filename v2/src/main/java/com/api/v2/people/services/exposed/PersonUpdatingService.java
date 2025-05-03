package com.api.v2.people.services.exposed;

import com.api.v2.people.domain.exposed.Person;
import com.api.v2.people.requests.PersonUpdatingDto;
import reactor.core.publisher.Mono;

public interface PersonUpdatingService {
    Mono<Person> update(Person oldPerson, PersonUpdatingDto updatingDto);
}
