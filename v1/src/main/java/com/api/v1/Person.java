package com.api.v1;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Document(collection = "People")
public class Person {

    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    @Indexed(unique = true)
    private String sin; // Single Identification Number
    @Indexed(unique = true)
    private String email;
    private String phoneNumber;
    private Address address;

    public Person() {
    }

    private Person(
            String firstName,
            String middleName,
            String lastName,
            LocalDate birthDate,
            String sin,
            String email,
            String phoneNumber,
            Address address
    ) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sin = sin;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Person of(@Valid PersonRegistrationDto dto) {
        return new Person(
                dto.firstName(),
                dto.middleName(),
                dto.lastName(),
                dto.birthDate(),
                dto.sin(),
                dto.email(),
                dto.phoneNumber(),
                dto.address()
        );
    }

    public String getFullName() {
        if (middleName == null) {
            return "%s %s".formatted(firstName, lastName);
        }
        return "%s %s %s".formatted(firstName, middleName, lastName);
    }
}
