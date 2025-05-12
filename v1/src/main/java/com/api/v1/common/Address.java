package com.api.v1.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Address(
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        @Size(min = 10, max = 10)
        String zipcode
) {
}
