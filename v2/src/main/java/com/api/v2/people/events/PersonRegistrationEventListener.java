package com.api.v2.people.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;

public class PersonRegistrationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRegistrationEventListener.class);

    @ApplicationModuleListener
    public void listen(PersonRegistrationEvent event) {
    }
}
