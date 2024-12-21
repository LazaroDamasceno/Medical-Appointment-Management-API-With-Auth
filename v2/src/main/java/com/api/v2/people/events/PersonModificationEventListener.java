package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonModificationDto;
import com.api.v2.people.services.PersonModificationService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class PersonModificationEventListener {

    private final PersonModificationService modificationService;

    public PersonModificationEventListener(PersonModificationService modificationService) {
        this.modificationService = modificationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listener(Person person, PersonModificationDto modificationDto) {
        return modificationService
                .modify(person, modificationDto)
                .then();
    }
}
