package com.api.v2.doctors.services

import com.api.v2.doctors.requests.DoctorRegistrationDTO
import com.api.v2.doctors.DoctorResponseDTO
import org.springframework.http.ResponseEntity

interface DoctorRegistrationService {
    fun register(registrationDTO: DoctorRegistrationDTO): ResponseEntity<DoctorResponseDTO>
}