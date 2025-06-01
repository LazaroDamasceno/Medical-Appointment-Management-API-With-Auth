package com.api.v2.medical_slots.services

import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

interface MedicalSlotRegistrationService {
    fun register(doctorLicenseNumber: String, availableAt: LocalDateTime): ResponseEntity<MedicalSlotResponseDTO>
}