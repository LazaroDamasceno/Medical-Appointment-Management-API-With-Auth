package com.api.v2.medical_slots.services

import org.springframework.http.ResponseEntity

interface MedicalSlotManagementService {
    fun cancel(licenseNumber: String, slotId: String): ResponseEntity<Void>
    fun complete(licenseNumber: String, slotId: String): ResponseEntity<Void>
}