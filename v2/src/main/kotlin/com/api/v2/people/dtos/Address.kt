package com.api.v2.people.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class Address(
    @NotBlank
    val string: String,
    @NotBlank
    val city: String,
    @NotBlank
    @Size(min = 5, max = 5)
    val zipcode: String
)