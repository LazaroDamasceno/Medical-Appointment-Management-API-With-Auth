package com.api.v2.doctors.responses

import org.springframework.hateoas.RepresentationModel

class DoctorResponseDTO(
    val licenseNumber: String,
    val fullName: String
): RepresentationModel<DoctorResponseDTO>()
