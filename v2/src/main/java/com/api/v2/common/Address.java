package com.api.v2.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Address(
        @NotBlank
        String street,
        @NotBlank
        String city,
        @Valid
        States state,
        @NotBlank
        @Size(min = 5, max = 5)
        String zipcode
) {
}
