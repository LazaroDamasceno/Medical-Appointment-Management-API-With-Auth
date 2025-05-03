package com.api.v1.people.services.exposed;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonRegistrationDto;
import reactor.core.publisher.Mono;

public interface PersonRegistrationService {
    Mono<Person> register(PersonRegistrationDto registrationDto);
}
