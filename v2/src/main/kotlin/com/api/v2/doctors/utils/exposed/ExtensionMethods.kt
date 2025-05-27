package com.api.v2.doctors.utils.exposed

import com.api.v2.doctors.domain.exposed.Doctor
import com.api.v2.doctors.responses.DoctorResponseDTO
import com.api.v2.people.utils.fullName

fun Doctor.toDTO(): DoctorResponseDTO {
    return DoctorResponseDTO(
        this.licenseNumber,
        this.person.fullName()
    )
}