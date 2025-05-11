package com.api.v1.people.domain;

import com.api.v1.people.people.requests.PersonRegistrationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Person(
        @Id
        String id,
        String firstName,
        String middleName,
        String lastName,
        String sin,
        LocalDateTime birthDate,
        String email,
        LocalDateTime createdAt
) {

    public static Person of(PersonRegistrationDto registrationDto) {
        return new Person(
                UUID.randomUUID().toString(),
                registrationDto.firstName(),
                registrationDto.middleName(),
                registrationDto.lastName(),
                registrationDto.sin(),
                registrationDto.birthDate(),
                registrationDto.email(),
                LocalDateTime.now()
        );
    }
}
