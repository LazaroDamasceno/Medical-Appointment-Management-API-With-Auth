package com.api.v2.people.events;

import com.api.v2.people.domain.Person;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class PersonRegistrationEventListener {

    @ApplicationModuleListener
    public Mono<Person> listen(PersonRegistrationEvent event) {
        return Mono.just(event.person());
    }
}
