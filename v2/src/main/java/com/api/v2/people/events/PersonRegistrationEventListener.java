package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonRegistrationDto;
import com.api.v2.people.services.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class PersonRegistrationEventListener {

    private final PersonRegistrationService personRegistrationService;

    public PersonRegistrationEventListener(PersonRegistrationService personRegistrationService) {
        this.personRegistrationService = personRegistrationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listener(@Valid PersonRegistrationDto registrationDto) {
        return personRegistrationService
                .register(registrationDto)
                .then();
    }
}
