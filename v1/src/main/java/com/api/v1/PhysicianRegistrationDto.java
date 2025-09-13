package com.api.v1;

import jakarta.validation.Valid;

public record PhysicianRegistrationDto(
        @Valid
        Person person,
        @Valid
        MedicalLicenseNumber licenseNumber
) {
}
