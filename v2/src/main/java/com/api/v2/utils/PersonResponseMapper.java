package com.api.v2.utils;

import com.api.v2.domain.Person;
import com.api.v2.dtos.PersonResponseDto;

public class PersonResponseMapper {

    public static PersonResponseDto map(Person person) {
        return new PersonResponseDto(
                person.getFullName(),
                person.getBirthDate(),
                person.getSsn(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getGender()
        );
    }
}
