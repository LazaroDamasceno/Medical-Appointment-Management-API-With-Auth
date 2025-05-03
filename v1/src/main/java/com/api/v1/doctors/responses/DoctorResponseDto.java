package com.api.v1.doctors.responses;

import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.enums.DoctorStatus;

public record DoctorResponseDto(
        String fullName,
        DoctorStatus status,
        MedicalLicenseNumber medicalLicenseNumber
) {
}
