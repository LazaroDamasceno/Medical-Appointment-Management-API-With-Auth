package com.api.v2.services;

import com.api.v2.domain.Person;
import com.api.v2.dtos.PersonRegistrationDto;
import reactor.core.publisher.Mono;

public interface PersonRegistrationService {
    Mono<Person> register(PersonRegistrationDto registrationDto);
}
