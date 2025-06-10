package com.api.v2.medical_slots.controllers

import com.api.v2.medical_slots.MedicalSlotResponseDTO
import com.api.v2.medical_slots.services.MedicalSlotRetrievalService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/medical-slots")
class MedicalSlotController(
    private val retrievalService: MedicalSlotRetrievalService
) {

    @GetMapping("{id}/{medicalLicenseNumber}")
    fun findByIdAndDoctor(
        @PathVariable id: String,
        @PathVariable medicalLicenseNumber: String
    ): ResponseEntity<MedicalSlotResponseDTO> {
        return retrievalService.findByIdAndDoctor(id, medicalLicenseNumber)
    }

    @GetMapping("{medicalLicenseNumber}")
    fun findAllByDoctor(
        @PathVariable medicalLicenseNumber: String,
        pageable: Pageable
    ): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        return retrievalService.findAllByDoctor(medicalLicenseNumber, pageable)
    }

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        return retrievalService.findAll( pageable)
    }
}