package com.api.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalLicenseNumber(
        String licenseNumber,
        UsStates state
) {

    public static MedicalLicenseNumber of(
            @NotBlank
            @Size(min=4, max=10)
            String licenseNumber,
            @NotNull
            UsStates state
    ) {
        return new MedicalLicenseNumber(licenseNumber, state);
    }
}
