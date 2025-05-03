package com.api.v1.doctors.responses;

import com.api.v1.doctors.domain.MedicalLicenseNumber;

public record DoctorResponseDto(
        String fullName,
        MedicalLicenseNumber medicalLicenseNumber
) {
}
