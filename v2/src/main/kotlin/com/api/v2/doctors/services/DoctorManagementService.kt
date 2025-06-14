package com.api.v2.doctors.services

import com.api.v2.people.PersonUpdateDTO
import org.springframework.http.ResponseEntity

interface DoctorManagementService {
    fun terminate(licenseNumber: String): ResponseEntity<Unit>
    fun rehire(licenseNumber: String): ResponseEntity<Unit>
    fun update(licenseNumber: String, updateDTO: PersonUpdateDTO): ResponseEntity<Unit>
}