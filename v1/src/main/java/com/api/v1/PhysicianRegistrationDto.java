package com.api.v1;

import jakarta.validation.Valid;

public record PhysicianRegistrationDto(
        Person person,
        MedicalLicenseNumber licenseNumber
) {

    public static PhysicianRegistrationDto of(
            @Valid Person person,
            @Valid MedicalLicenseNumber licenseNumber
    ) {
        return new PhysicianRegistrationDto(person, licenseNumber);
    }
}
