package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonRegistrationDto;
import com.api.v2.people.services.PersonRegistrationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonRegistrationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final PersonRegistrationService registrationService;

    public PersonRegistrationEventPublisher(
            ApplicationEventPublisher eventPublisher,
            PersonRegistrationService registrationService
    ) {
        this.eventPublisher = eventPublisher;
        this.registrationService = registrationService;
    }

    public Mono<Person> publish(PersonRegistrationDto registrationDto) {
        return registrationService
                .register(registrationDto)
                .flatMap(person -> {
                    eventPublisher.publishEvent(person);
                    return Mono.just(person);
                });
    }
}
