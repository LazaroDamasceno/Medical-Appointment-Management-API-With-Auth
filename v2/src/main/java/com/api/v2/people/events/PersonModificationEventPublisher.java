package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonModificationDto;
import com.api.v2.people.services.PersonModificationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonModificationEventPublisher {

    private final PersonModificationService modificationService;
    private final ApplicationEventPublisher eventPublisher;

    public PersonModificationEventPublisher(
            PersonModificationService modificationService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.modificationService = modificationService;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Person> publish(Person person, PersonModificationDto modificationDto) {
        return modificationService
                .modify(person, modificationDto)
                .flatMap(modifiedPerson -> {
                    eventPublisher.publishEvent(modifiedPerson);
                    return Mono.just(modifiedPerson);
                });
    }
}
