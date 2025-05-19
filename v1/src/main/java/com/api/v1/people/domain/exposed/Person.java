package com.api.v1.people.domain.exposed;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDTO;
import com.api.v1.people.requests.PersonUpdatingDTO;
import jakarta.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
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
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Person() {}

    private Person(@Valid PersonRegistrationDTO registrationDto) {
        this.id = UUID.randomUUID().toString();
        this.firstName = registrationDto.firstName();
        this.middleName = registrationDto.middleName();
        this.lastName = registrationDto.lastName();
        this.sin = registrationDto.sin();
        this.birthDate = registrationDto.birthDate();
        this.email = registrationDto.email();
        this.gender = registrationDto.gender();
        this.address = registrationDto.address();
        this.createdAt = LocalDateTime.now();
    }

    public static Person of(@Valid PersonRegistrationDTO registrationDto) {
        return new Person(registrationDto);
    }

    public void update(@Valid PersonUpdatingDTO updatingDto) {
        this.firstName = updatingDto.firstName();
        this.middleName = updatingDto.middleName();
        this.lastName = updatingDto.lastName();
        this.birthDate = updatingDto.birthDate();
        this.email = updatingDto.email();
        this.gender = updatingDto.gender();
        this.address = updatingDto.address();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSin() {
        return sin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}