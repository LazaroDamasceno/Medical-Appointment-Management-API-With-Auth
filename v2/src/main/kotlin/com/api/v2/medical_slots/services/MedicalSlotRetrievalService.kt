package com.api.v2.medical_slots.services

import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface MedicalSlotRetrievalService {
    fun findById(doctorLicenseNumber: String, id: String): ResponseEntity<MedicalSlotResponseDTO>
    fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>>
}