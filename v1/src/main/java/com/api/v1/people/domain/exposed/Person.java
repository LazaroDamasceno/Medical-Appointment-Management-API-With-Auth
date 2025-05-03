package com.api.v1.people.domain.exposed;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
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
    Address address,
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
                registrationDto.address(),
                LocalDateTime.now()
        );
    }

    public static Person of(PersonUpdatingDto updatingDto, String ssn) {
        return new Person(
                UUID.randomUUID().toString(),
                updatingDto.firstName(),
                updatingDto.middleName(),
                updatingDto.lastName(),
                ssn,
                updatingDto.birthDate(),
                updatingDto.phoneNumber(),
                updatingDto.gender(),
                updatingDto.email(),
                updatingDto.address(),
                LocalDateTime.now()
        );
    }

}
