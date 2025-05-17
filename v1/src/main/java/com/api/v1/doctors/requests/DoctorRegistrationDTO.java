package com.api.v1.doctors.requests;

import com.api.v1.common.LicenseNumber;
import com.api.v1.people.requests.PersonRegistrationDTO;
import jakarta.validation.Valid;

public record DoctorRegistrationDTO(
        @LicenseNumber
        String licenseNumber,
        @Valid
        PersonRegistrationDTO person
) {
}
