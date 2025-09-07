package com.api.v1;

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
        @NotNull
        LocalDate birthDate,
        @NotBlank
        @Size(min=10, max=10)
        String sin,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min=10, max=10)
        String phoneNumber,
        @Valid
        Address address
) {
}
