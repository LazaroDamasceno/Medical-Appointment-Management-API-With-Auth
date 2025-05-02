package com.api.v2.people.domain.exposed;

import com.api.v2.people.enums.Gender;
import com.api.v2.people.requests.PersonRegistrationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Person (
    @Id
    String id,
    String firstName,
    String middleName,
    String lastName,
    String ssn,
    LocalDate birthDate,
    String phoneNumber,
    Gender gender,
    String email,
    LocalDateTime createdAt
) {

    public static Person of(PersonRegistrationDto registrationDto) {
        return new Person(
                UUID.randomUUID().toString(),
                registrationDto.firstName(),
                registrationDto.middleName(),
                registrationDto.lastName(),
                registrationDto.ssn(),
                registrationDto.birthDate(),
                registrationDto.phoneNumber(),
                registrationDto.gender(),
                registrationDto.email(),
                LocalDateTime.now()
        );
    }

}
