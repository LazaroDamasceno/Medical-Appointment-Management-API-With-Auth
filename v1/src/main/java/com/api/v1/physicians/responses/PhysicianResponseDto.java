package com.api.v1.physicians.responses;

import com.api.v1.physicians.helpers.MedicalLicense;
import com.api.v1.physicians.models.Physician;
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
