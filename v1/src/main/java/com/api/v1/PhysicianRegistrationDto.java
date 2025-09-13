package com.api.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PhysicianRegistrationDto(
        Person person,
        MedicalLicenseNumber licenseNumber,
        MedicalSpecialization specialization
) {

    public static PhysicianRegistrationDto of(
            @Valid Person person,
            @Valid MedicalLicenseNumber licenseNumber,
            @NotNull MedicalSpecialization specialization
    ) {
        return new PhysicianRegistrationDto(
                person,
                licenseNumber,
                specialization
        );
    }
}
