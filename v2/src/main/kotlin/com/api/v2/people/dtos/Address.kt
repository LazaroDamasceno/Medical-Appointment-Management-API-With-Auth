package com.api.v2.people.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class Address(
    @NotBlank
    val street: String,
    @NotBlank
    val city: String,
    @NotBlank
    @Size(min = 5, max = 5)
    val zipcode: String
)