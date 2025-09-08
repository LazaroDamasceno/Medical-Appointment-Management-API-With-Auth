package com.api.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record PhysicianRegistrationDto(
        @Size(min=8, max=8)
        String licenseNumber,
        @Valid
        Person person
) {
}
