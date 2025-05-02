package com.api.v2.people.utils;

import com.api.v2.people.domain.exposed.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FullNameFormatter {

    public String format(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        if (person.getMiddleName() == null) {
            return String.format("%s %s", firstName, lastName);
        }
        String middleName = person.getMiddleName();
        return String.format("%s %s %s", firstName, middleName, lastName);
    }

}
