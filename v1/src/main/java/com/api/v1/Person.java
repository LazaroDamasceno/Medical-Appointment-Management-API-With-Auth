package com.api.v1;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Person {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    // Single Identification Number
    private String sin;
    private String email;
    private String phoneNumber;
    private Address address;
}
