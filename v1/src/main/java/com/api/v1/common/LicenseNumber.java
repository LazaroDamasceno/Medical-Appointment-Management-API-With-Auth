package com.api.v1.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LicenseNumber(
        @NotNull
        Job job,
        @NotBlank
        @Size(min = 10, max = 10)
        String number
) {
}
