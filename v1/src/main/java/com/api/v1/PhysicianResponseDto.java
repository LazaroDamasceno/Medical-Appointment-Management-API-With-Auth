package com.api.v1;

import jakarta.validation.constraints.NotNull;

public record PhysicianResponseDto(
        String fullName,
        MedicalLicense medicalLicense
) {

    public static PhysicianResponseDto from(@NotNull Physician physician) {
        return new PhysicianResponseDto(
                physician.getPerson().getFullName(),
                physician.getMedicalLicense()
        );
    }
}
