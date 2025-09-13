package com.api.v1.physicians.requests;

import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.physicians.helpers.MedicalLicense;
import com.api.v1.physicians.helpers.MedicalSpecialization;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PhysicianRegistrationDto(
        PersonRegistrationDto person,
        MedicalLicense licenseNumber,
        MedicalSpecialization specialization
) {

    public static PhysicianRegistrationDto of(
            @Valid PersonRegistrationDto person,
            @Valid MedicalLicense licenseNumber,
            @NotNull MedicalSpecialization specialization
    ) {
        return new PhysicianRegistrationDto(
                person,
                licenseNumber,
                specialization
        );
    }
}
