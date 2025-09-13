package com.api.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonRegistrationDto(
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthDate,
        String sin,
        String email,
        String phoneNumber,
        Address address
) {

    public static PersonRegistrationDto of(
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
        return new PersonRegistrationDto(
                firstName,
                middleName,
                lastName,
                birthDate,
                sin,
                email,
                phoneNumber,
                address
        );
    }
}
