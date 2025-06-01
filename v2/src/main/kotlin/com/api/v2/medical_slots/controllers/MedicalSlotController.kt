package com.api.v2.medical_slots.controllers

import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import com.api.v2.medical_slots.services.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("api/v2/medical-slots")
class MedicalSlotController {

    private val registrationService: MedicalSlotRegistrationService
    private val managementService: MedicalSlotManagementService
    private val retrievalService: MedicalSlotRetrievalService

    constructor(
        registrationService: MedicalSlotRegistrationService,
        managementService: MedicalSlotManagementService,
        retrievalService: MedicalSlotRetrievalService
    ) {
        this.registrationService = registrationService
        this.managementService = managementService
        this.retrievalService = retrievalService
    }

    @PostMapping("{doctorLicenseNumber}/{id}")
    fun register(
        @PathVariable doctorLicenseNumber: String,
        @PathVariable availableAt: LocalDateTime
    ): ResponseEntity<MedicalSlotResponseDTO> {
      return registrationService.register(doctorLicenseNumber, availableAt)
    }

    @PatchMapping("{doctorLicenseNumber}/{id}/cancellation")
    fun cancel(
        @PathVariable doctorLicenseNumber: String,
        @PathVariable id: String
    ): ResponseEntity<Void> {
        return managementService.cancel(doctorLicenseNumber, id)
    }

    @PatchMapping("{doctorLicenseNumber}/{id}/completion")
    fun complete(
        @PathVariable doctorLicenseNumber: String,
        @PathVariable id: String
    ): ResponseEntity<Void> {
        return managementService.complete(doctorLicenseNumber, id)
    }

    @GetMapping("{doctorLicenseNumber}/{id}")
    fun findById(
        @PathVariable doctorLicenseNumber: String,
        @PathVariable id: String
    ): ResponseEntity<MedicalSlotResponseDTO> {
        return retrievalService.findById(doctorLicenseNumber, id)
    }

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        return retrievalService.findAll(pageable)
    }
}