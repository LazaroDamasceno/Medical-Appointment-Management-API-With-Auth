package com.api.v1.people.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Address(
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        @Size(min = 5, max = 5)
        String zipcode
) {
}
