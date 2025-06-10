package com.api.v2.doctors.services

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.people.PersonRegistrationDTO
import org.springframework.http.ResponseEntity

interface DoctorRegistrationService {
    fun register(licenseNumber: String, personDTO: PersonRegistrationDTO): ResponseEntity<DoctorResponseDTO>
}