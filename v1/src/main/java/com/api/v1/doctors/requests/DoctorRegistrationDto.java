package com.api.v1.doctors.requests;

import com.api.v1.doctors.dtos.MedicalLicenseNumber;
import com.api.v1.doctors.enums.MedicalSpeciality;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DoctorRegistrationDto(
        @Valid
        PersonRegistrationDto person,
        @NotNull
        MedicalSpeciality medicalSpeciality,
        @Valid
        MedicalLicenseNumber licenseNumber
) {
}
