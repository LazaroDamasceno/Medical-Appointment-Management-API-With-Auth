package com.api.v2.dtos;

public record DoctorRegistrationDto(
        String medicalLicenseNumber,
        PersonRegistrationDto personRegistrationDto
) {
}
