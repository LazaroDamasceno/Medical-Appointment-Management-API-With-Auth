package com.api.v2.people.utils;

import com.api.v2.people.domain.exposed.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FullNameFormatter {

    public String format(Person person) {
        if (person.middleName() == null) {
            return String.format("%s %s", person.firstName(), person.lastName());
        }
        return String.format("%s %s %s", person.firstName(), person.middleName(), person.lastName());
    }

}
