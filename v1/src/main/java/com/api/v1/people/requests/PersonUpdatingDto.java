package com.api.v1.people.requests;

import com.api.v1.people.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonUpdatingDto(
        @NotBlank
        String firstName,
        String middleName,
        @NotBlank
        String lastName,
        @NotBlank
        @NotNull
        LocalDate birthDate,
        @NotBlank
        @Email
        String email,
        @NotNull
        Gender gender
) {
}
