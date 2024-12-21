package com.api.v2.people.services;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonRegistrationDto;
import reactor.core.publisher.Mono;

public interface PersonRegistrationService {
    Mono<Person> register(PersonRegistrationDto registrationDto);
}
