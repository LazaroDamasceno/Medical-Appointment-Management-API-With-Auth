package com.api.v2.medical_slots.services

import com.api.v2.medical_slots.MedicalSlotResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface MedicalSlotRetrievalService {
    fun findByIdAndDoctor(id: String, medicalLicenseNumber: String): ResponseEntity<MedicalSlotResponseDTO>
    fun findAllByDoctor(medicalLicenseNumber: String, pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>>
    fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>>
}