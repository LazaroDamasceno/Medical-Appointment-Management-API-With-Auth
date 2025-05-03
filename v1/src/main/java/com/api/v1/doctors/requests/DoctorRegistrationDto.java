package com.api.v1.doctors.requests;

import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;

public record DoctorRegistrationDto(
        @Valid
        PersonRegistrationDto person,
        @Valid
        MedicalLicenseNumber medicalLicenseNumber
) {
}
