package com.api.v1.people;

import com.api.v1.people.domain.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FullNameFormatter {

    public String format(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        if  (person.getMiddleName() == null) {
            return "%s %s".formatted(firstName, lastName);
        }
        String middleName = person.getMiddleName();
        return "%s %s %s".formatted(firstName, middleName, lastName);
    }

}
