package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonRegistrationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public PersonRegistrationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Mono<Person> publish(Mono<Person> mono) {
        eventPublisher.publishEvent(mono);
        return Mono.empty();
    }
}
