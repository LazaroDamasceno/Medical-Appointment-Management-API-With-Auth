package com.api.v1;

import jakarta.validation.Valid;

public record PhysicianRegistrationDto(
        @Valid
        MedicalLicenseNumber licenseNumber,
        @Valid
        Person person
) {
}
