package com.api.v2.medical_slots.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record MedicalSlotRegistrationDto(
        String medicalLicenseNumber,
        @NotNull LocalDateTime availableAt
) {
}
