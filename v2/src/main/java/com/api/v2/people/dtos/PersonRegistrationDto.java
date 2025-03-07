package com.api.v2.people.dtos;

import com.api.v2.people.utils.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonRegistrationDto(
        @NotBlank String firstName,
        String middleName,
        @NotBlank String lastName,
        @NotNull LocalDate birthDate,
        @NotBlank @Size(min = 9, max = 9) String ssn,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 10, max = 10) String phoneNumber,
        @NotNull Gender gender
) {
}
