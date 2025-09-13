package com.api.v1.physicians.helpers;

import com.api.v1.UsState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalLicense(
        String id,
        UsState state
) {

    public static MedicalLicense of(
            @NotBlank
            @Size(min=4, max=10)
            String id,
            @NotNull
            UsState state
    ) {
        return new MedicalLicense(id, state);
    }
}
