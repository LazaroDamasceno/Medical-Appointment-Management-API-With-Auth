package com.api.v2.people;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

}
