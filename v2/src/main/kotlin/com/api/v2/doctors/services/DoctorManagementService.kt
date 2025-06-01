package com.api.v2.doctors.services

import com.api.v2.people.requests.PersonUpdateDTO
import org.springframework.http.ResponseEntity

interface DoctorManagementService {
    fun terminate(licenseNumber: String): ResponseEntity<Void>
    fun rehire(licenseNumber: String): ResponseEntity<Void>
    fun update(licenseNumber: String, updateDTO: PersonUpdateDTO): ResponseEntity<Void>
}