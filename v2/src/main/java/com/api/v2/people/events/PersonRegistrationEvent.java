package com.api.v2.people.events;

import com.api.v2.people.domain.Person;

public record PersonRegistrationEvent(Person person) {

    public static PersonRegistrationEvent create(Person person) {
        return new PersonRegistrationEvent(person);
    }
}
