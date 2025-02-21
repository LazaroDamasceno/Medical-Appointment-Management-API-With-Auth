package com.api.v2.doctors.dtos;

import com.api.v2.common.MLN;

public record DoctorResponseDto(
        String fullName,
        @MLN String medicalLicenseNumber
) {
}
