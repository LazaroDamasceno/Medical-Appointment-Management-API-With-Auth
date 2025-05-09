package com.api.v1.people.requests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonRegistrationDto(
        @NotBlank
        String firstName,
        String middleName,
        @NotBlank
        String lastName,
        @NotBlank
        @Size(min = 10, max = 10)
        // SIN = Single Identification Number
        String sin,
        @NotNull
        LocalDate birthDate,
        @NotBlank
        @Size(min = 10, max = 10)
        String phoneNumber,
        @NotNull
        Gender gender,
        @NotBlank
        @Email
        String email,
        @Valid
        Address address
) {
}
