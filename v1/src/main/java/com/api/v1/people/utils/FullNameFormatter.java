package com.api.v1.people.utils;

import com.api.v1.people.domain.exposed.Person;

public final class FullNameFormatter {

    private FullNameFormatter() {
    }

    public static String format(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        if  (person.getMiddleName() == null) {
            return "%s %s".formatted(firstName, lastName);
        }
        String middleName = person.getMiddleName();
        return "%s %s %s".formatted(firstName, middleName, lastName);
    }

}
