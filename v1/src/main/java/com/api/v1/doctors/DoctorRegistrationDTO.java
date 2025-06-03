package com.api.v1.doctors;

import com.api.v1.common.LicenseNumber;
import com.api.v1.people.PersonRegistrationDTO;
import jakarta.validation.Valid;

public record DoctorRegistrationDTO(
        @LicenseNumber
        String licenseNumber,
        @Valid
        PersonRegistrationDTO person
) {
}
