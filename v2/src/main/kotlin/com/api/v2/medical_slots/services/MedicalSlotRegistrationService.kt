package com.api.v2.medical_slots.services

import com.api.v2.medical_slots.DefaultMedicalSlotResponseDTO
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

interface MedicalSlotRegistrationService {
    fun register(medicalLicenseNumber: String, availableAt: LocalDateTime): ResponseEntity<DefaultMedicalSlotResponseDTO>
}