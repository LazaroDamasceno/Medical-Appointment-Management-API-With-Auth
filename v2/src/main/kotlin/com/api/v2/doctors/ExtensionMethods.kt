package com.api.v2.doctors

import com.api.v2.people.fullName

fun Doctor.toDTO(): DoctorResponseDTO {
    return DoctorResponseDTO(
        this.licenseNumber,
        this.person.fullName()
    )
}