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
public class Person {

    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;
    private LocalDate birthDate;
    private String phoneNumber;
    private Gender gender;
    private String email;
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Person() {
    }

    private Person(String firstName,
                  String middleName,
                  String lastName,
                  String ssn,
                  LocalDate birthDate,
                  String phoneNumber,
                  Gender gender,
                  String email,
                  Address address
    ) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.createdAt = LocalDateTime.now();
    }

    public static Person of(PersonRegistrationDto registrationDto) {
        return new Person(
                registrationDto.firstName(),
                registrationDto.middleName(),
                registrationDto.lastName(),
                registrationDto.ssn(),
                registrationDto.birthDate(),
                registrationDto.phoneNumber(),
                registrationDto.gender(),
                registrationDto.email(),
                registrationDto.address()
        );
    }

    public void update(PersonUpdatingDto updatingDto) {
        this.firstName = updatingDto.firstName();
        this.middleName = updatingDto.middleName();
        this.lastName = updatingDto.lastName();
        this.birthDate = updatingDto.birthDate();
        this.phoneNumber = updatingDto.phoneNumber();
        this.gender = updatingDto.gender();
        this.email = updatingDto.email();
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

    public String getSsn() {
        return ssn;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
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
