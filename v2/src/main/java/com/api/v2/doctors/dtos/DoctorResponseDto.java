package com.api.v2.doctors.dtos;

import com.api.v2.people.dtos.PersonResponseDto;

public record DoctorResponseDto(
        String medicalLicenseNumber,
        PersonResponseDto personResponseDto
) {
}
