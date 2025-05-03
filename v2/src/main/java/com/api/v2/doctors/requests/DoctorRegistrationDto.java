package com.api.v2.doctors.requests;

import com.api.v2.doctors.domain.MedicalLicenseNumber;
import com.api.v2.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;

public record DoctorRegistrationDto(
        @Valid
        PersonRegistrationDto person,
        @Valid
        MedicalLicenseNumber medicalLicenseNumber
) {
}
