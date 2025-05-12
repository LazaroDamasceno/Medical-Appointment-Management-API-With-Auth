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
        String sin,
        @NotNull
        LocalDate birthDate,
        @NotBlank
        @Email
        String email,
        @NotNull
        Gender gender,
        @Valid
        Address address
) {
}
