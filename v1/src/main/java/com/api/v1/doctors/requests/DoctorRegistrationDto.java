package com.api.v1.doctors.requests;

import com.api.v1.common.LicenseNumber;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;

public record DoctorRegistrationDto(
        @LicenseNumber
        String licenseNumber,
        @Valid
        PersonRegistrationDto person
) {
}
