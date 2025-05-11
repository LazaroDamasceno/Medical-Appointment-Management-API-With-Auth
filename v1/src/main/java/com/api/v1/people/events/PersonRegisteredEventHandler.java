package com.api.v1.people.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonRegisteredEventHandler {

    @EventListener
    public void handlePersonRegisteredEvent(PersonRegisteredEvent event) {
        log.info("Person with ID {} was registered.", event.getPerson().id());
    }
}