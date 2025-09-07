package com.api.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Address(
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotNull
        States state,
        @NotBlank
        @Size(min=5, max=5)
        String zipcode
) {
}
