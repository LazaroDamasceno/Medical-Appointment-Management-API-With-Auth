package com.api.v1.people.domain;

import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
public class Person {

    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String sin;
    private LocalDate birthDate;
    private String email;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Person() {
    }

    private Person(String id,
                   String firstName,
                   String middleName,
                   String lastName,
                   String sin,
                   LocalDate birthDate,
                   String email,
                   Gender gender,
                   LocalDateTime createdAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.sin = sin;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.createdAt = createdAt;
    }

    public static Person of(PersonRegistrationDto registrationDto) {
        return new Person(
                UUID.randomUUID().toString(),
                registrationDto.firstName(),
                registrationDto.middleName(),
                registrationDto.lastName(),
                registrationDto.sin(),
                registrationDto.birthDate(),
                registrationDto.email(),
                registrationDto.gender(),
                LocalDateTime.now()
        );
    }

    public void update(PersonUpdatingDto updatingDto) {
        this.firstName = updatingDto.firstName();
        this.middleName = updatingDto.middleName();
        this.lastName = updatingDto.lastName();
        this.birthDate = updatingDto.birthDate();
        this.email = updatingDto.email();
        this.gender = updatingDto.gender();
        this.updatedAt = LocalDateTime.now();
    }
}