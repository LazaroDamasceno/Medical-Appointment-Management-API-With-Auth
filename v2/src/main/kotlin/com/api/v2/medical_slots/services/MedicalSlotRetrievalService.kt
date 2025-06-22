package com.api.v2.medical_slots.services

import com.api.v2.medical_slots.DefaultMedicalSlotResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface MedicalSlotRetrievalService {
    fun findByIdAndDoctor(id: String, medicalLicenseNumber: String): ResponseEntity<DefaultMedicalSlotResponseDTO>
    fun findAllByDoctor(medicalLicenseNumber: String, pageable: Pageable): ResponseEntity<Page<DefaultMedicalSlotResponseDTO>>
    fun findAll(pageable: Pageable): ResponseEntity<Page<DefaultMedicalSlotResponseDTO>>
}