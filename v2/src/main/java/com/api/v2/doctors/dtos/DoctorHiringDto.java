package com.api.v2.doctors.dtos;

import com.api.v2.people.dtos.PersonRegistrationDto;

public record DoctorHiringDto(
        String medicalLicenseNumber,
        PersonRegistrationDto personRegistrationDto
) {
}
