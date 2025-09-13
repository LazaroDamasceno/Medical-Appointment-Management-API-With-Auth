package com.api.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalLicenseNumber(
        @NotBlank
        @Size(min=8, max=8)
        String licenseNumber,
        @NotNull
        UsStates state
) {
}
