package com.api.v2.medical_slots.dtos;

import java.time.LocalDateTime;

import com.api.v2.common.MLN;

import jakarta.validation.constraints.NotNull;

public record MedicalSlotRegistrationDto(
        @MLN String medicalLicenseNumber,
        @NotNull LocalDateTime availableAt
) {
}
