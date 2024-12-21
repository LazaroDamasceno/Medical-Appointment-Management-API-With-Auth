package com.api.v2.dtos;

public record DoctorHiringDto(
        String medicalLicenseNumber,
        PersonRegistrationDto personRegistrationDto
) {
}
