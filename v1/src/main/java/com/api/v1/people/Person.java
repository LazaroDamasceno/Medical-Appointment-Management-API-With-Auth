package com.api.v1.people;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import jakarta.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "People")
public record Person(
        @Id
        String id,
        String firstName,
        String middleName,
        String lastName,
        @Indexed(unique = true)
        String sin,
        LocalDate birthDate,
        @Indexed(unique = true)
        String email,
        Gender gender,
        Address address,
        String phoneNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static Person of(@Valid PersonRegistrationDTO registrationDto) {
        return new Person(
                UUID.randomUUID().toString(),
                registrationDto.firstName(),
                registrationDto.middleName(),
                registrationDto.lastName(),
                registrationDto.sin(),
                registrationDto.birthDate(),
                registrationDto.email(),
                registrationDto.gender(),
                registrationDto.address(),
                registrationDto.phoneNumber(),
                LocalDateTime.now(),
                null
        );
    }

    public Person update(@Valid PersonUpdateDTO updatingDto) {
        return new Person(
                this.id,
                updatingDto.firstName(),
                updatingDto.middleName(),
                updatingDto.lastName(),
                this.sin,
                updatingDto.birthDate(),
                updatingDto.email(),
                updatingDto.gender(),
                updatingDto.address(),
                updatingDto.phoneNumber(),
                this.createdAt,
                LocalDateTime.now()
        );
    }
}