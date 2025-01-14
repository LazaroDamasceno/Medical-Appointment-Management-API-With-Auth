package com.api.v2.doctors.dtos;

public record DoctorResponseDto(
        String fullName,
        String medicalLicenseNumber
) {
}
