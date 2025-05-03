package com.api.v2.doctors.responses;

import com.api.v2.doctors.domain.MedicalLicenseNumber;

public record DoctorResponseDto(
        String fullName,
        MedicalLicenseNumber medicalLicenseNumber
) {
}
