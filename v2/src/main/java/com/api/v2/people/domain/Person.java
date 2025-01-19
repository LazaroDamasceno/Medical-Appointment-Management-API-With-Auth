package com.api.v2.people.domain;

import com.api.v2.people.dtos.PersonModificationDto;
import com.api.v2.people.dtos.PersonRegistrationDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {

    @BsonId
    private ObjectId id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String ssn;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDateTime createdAt;
    private ZoneOffset createdAtZone;
    private LocalDateTime modifiedAt;
    private ZoneOffset modifiedAtZone;

    public Person() {
    }

    private Person(PersonRegistrationDto registrationDto) {
        this.id = new ObjectId();
        this.firstName = registrationDto.firstName();
        this.middleName = registrationDto.middleName();
        this.lastName = registrationDto.lastName();
        this.birthDate = registrationDto.birthDate();
        this.ssn = registrationDto.ssn();
        this.email = registrationDto.email();
        this.phoneNumber = registrationDto.phoneNumber();
        this.gender = registrationDto.gender();
        this.createdAt = OffsetDateTime.now().toLocalDateTime();
        this.createdAtZone = OffsetDateTime.now().getOffset();
    }

    public static Person create(PersonRegistrationDto registrationDto) {
        return new Person(registrationDto);
    }

    public void modify(PersonModificationDto modificationDto) {
        this.firstName = modificationDto.firstName();
        this.middleName = modificationDto.middleName();
        this.lastName = modificationDto.lastName();
        this.birthDate = modificationDto.birthDate();
        this.email = modificationDto.email();
        this.phoneNumber = modificationDto.phoneNumber();
        this.gender = modificationDto.gender();
        this.modifiedAt = OffsetDateTime.now().toLocalDateTime();
        this.modifiedAtZone = OffsetDateTime.now().getOffset();
    }

    public String getFullName() {
        if (middleName.isEmpty()) {
            return "%s %s".formatted(firstName, lastName);
        }
        return "%s %s %s".formatted(firstName, middleName, lastName);
    }

    public ObjectId getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneOffset getCreatedAtZone() {
        return createdAtZone;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public ZoneOffset getModifiedAtZone() {
        return modifiedAtZone;
    }
}
