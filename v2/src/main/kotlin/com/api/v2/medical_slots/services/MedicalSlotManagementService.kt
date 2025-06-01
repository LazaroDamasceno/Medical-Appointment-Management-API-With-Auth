package com.api.v2.medical_slots.services

import org.springframework.http.ResponseEntity

interface MedicalSlotManagementService {
    fun cancel(doctorLicenseNumber: String, id: String): ResponseEntity<Void>
    fun complete(doctorLicenseNumber: String, id: String): ResponseEntity<Void>
}