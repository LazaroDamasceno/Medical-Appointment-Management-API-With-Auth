package com.api.v2.doctors

import org.springframework.hateoas.RepresentationModel

class DoctorResponseDTO(
    val licenseNumber: String,
    val fullName: String
): RepresentationModel<DoctorResponseDTO>()
