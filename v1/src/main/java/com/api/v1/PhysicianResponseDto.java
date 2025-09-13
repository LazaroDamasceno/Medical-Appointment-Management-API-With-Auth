package com.api.v1;

public record PhysicianResponseDto(
        String fullName,
        MedicalLicenseNumber licenseNumber
) {
}
