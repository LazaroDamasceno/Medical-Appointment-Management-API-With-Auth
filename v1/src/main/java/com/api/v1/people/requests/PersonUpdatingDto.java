package com.api.v1.people.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PersonUpdatingDto(
        @NotBlank
        String firstName,
        String middleName,
        @NotBlank
        String lastName,
        @NotBlank
        @NotNull
        LocalDateTime birthDate,
        @NotBlank
        @Email
        String email
) {
}
