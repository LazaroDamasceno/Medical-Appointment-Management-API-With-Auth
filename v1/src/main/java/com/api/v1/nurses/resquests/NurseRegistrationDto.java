package com.api.v1.nurses.resquests;

import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NurseRegistrationDto(
        @Valid
        PersonRegistrationDto person,
        @NotBlank
        @Size(min = 10, max = 10)
        String licenseNumber
) {
}
