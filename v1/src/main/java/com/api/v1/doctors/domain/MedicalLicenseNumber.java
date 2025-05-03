package com.api.v1.doctors.domain;

import com.api.v1.common.States;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalLicenseNumber(
        @NotBlank
        @Size(min = 8, max = 8)
        String licenseNumber,
        @NotNull
        States state
) {
}
