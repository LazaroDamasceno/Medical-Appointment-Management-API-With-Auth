package com.api.v1.people.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PersonRegistrationDto(
        @NotBlank
        String firstName,
        String middleName,
        @NotBlank
        String lastName,
        @NotBlank
        @Size(min = 10, max = 10)
        String sin,
        @NotNull
        LocalDateTime birthDate,
        @NotBlank
        @Email
        String email
) {
}
