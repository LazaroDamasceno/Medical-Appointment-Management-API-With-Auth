package com.api.v2.dtos;

public record DoctorResponseDto(
        String medicalLicenseNumber,
        PersonResponseDto personResponseDto
) {
}
