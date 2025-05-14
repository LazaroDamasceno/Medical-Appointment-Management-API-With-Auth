package com.api.v2.people.requests

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PersonRegistrationDto(
    @NotBlank
    val firstName: String,
    val middleName: String?,
    @NotBlank
    val lastName: String,
    @NotBlank
    @Size(min = 10, max = 10)
    val sin: String,
    @NotNull
    val birthDate: LocalDate,
    @NotNull
    val gender: Gender,
    @Valid
    val address: Address,
    @NotBlank
    @Email
    var email: String,
    @NotBlank
    @Size(min = 10, max = 10)
    var phoneNumber: String
)