package com.api.v2.people.requests

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PersonUpdatingDTO(
    @NotBlank
    val firstName: String,
    val middleName: String?,
    @NotBlank
    val lastName: String,
    @NotNull
    val birthDate: LocalDate,
    @NotBlank
    @Email
    val email: String,
    @NotBlank
    @Size(min = 10, max = 10)
    val phoneNumber: String,
    @NotNull
    val gender: Gender,
    @Valid
    val address: Address
)
