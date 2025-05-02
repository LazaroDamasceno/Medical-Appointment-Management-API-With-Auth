package com.api.v2.people.domain;

import com.api.v2.people.enums.Gender;
import com.api.v2.people.requests.PersonRegistrationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor
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
    private LocalDateTime createdAt;

    private Person(PersonRegistrationDto registrationDto) {
        this.id = UUID.randomUUID().toString();
        this.firstName = registrationDto.firstName();
        this.middleName = registrationDto.middleName();
        this.lastName = registrationDto.lastName();
        this.ssn = registrationDto.ssn();
        this.birthDate = registrationDto.birthDate();
        this.phoneNumber = registrationDto.phoneNumber();
        this.gender = registrationDto.gender();
        this.email = registrationDto.email();
        this.createdAt = LocalDateTime.now();
    }

    public static Person of(PersonRegistrationDto registrationDto) {
        return new Person(registrationDto);
    }

}
