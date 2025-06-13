package com.api.v2.medical_slots.services

import org.springframework.http.ResponseEntity

interface MedicalSlotManagementService {
    fun cancel(medicalLicenseNumber: String, slotId: String): ResponseEntity<Void>
    fun complete(medicalLicenseNumber: String, slotId: String): ResponseEntity<Void>
}