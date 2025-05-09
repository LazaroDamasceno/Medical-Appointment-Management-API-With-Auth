package com.api.v1.doctors.dtos;

import com.api.v1.common.States;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalLicenseNumber(
        @NotBlank
        @Size(min = 10, max = 10)
        String number,
        @NotNull
        States state
) {
}
