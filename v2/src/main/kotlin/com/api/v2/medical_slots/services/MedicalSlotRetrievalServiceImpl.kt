package com.api.v2.medical_slots.services

import com.api.v2.doctors.utils.DoctorFinder
import com.api.v2.medical_slots.exceptions.InaccessibleMedicalSlotException
import com.api.v2.medical_slots.domain.MedicalSlot
import com.api.v2.medical_slots.controllers.MedicalSlotController
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import com.api.v2.medical_slots.utils.toDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MedicalSlotRetrievalServiceImpl: MedicalSlotRetrievalService {

    private val crudRepository: MedicalSlotCrudRepository
    private val doctorFinder: DoctorFinder

    constructor(crudRepository: MedicalSlotCrudRepository, doctorFinder: DoctorFinder) {
        this.crudRepository = crudRepository
        this.doctorFinder = doctorFinder
    }

    override fun findById(
        doctorLicenseNumber: String,
        id: String
    ): ResponseEntity<MedicalSlotResponseDTO> {
        val foundDoctor = doctorFinder.findByLicenseNumber(doctorLicenseNumber)
        val foundSlot = crudRepository.findByDoctorAndId(id, foundDoctor.id)
        if (foundSlot == null) {
            throw InaccessibleMedicalSlotException(foundDoctor.licenseNumber)
        }
        val dto = foundSlot.toDTO()
        dto.add(
            linkTo(methodOn(MedicalSlotController::class.java).findById(doctorLicenseNumber, id))
                .withSelfRel()
        )
        return ResponseEntity.ok(dto)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        val all = crudRepository
            .findAll(pageable)
            .map(MedicalSlot::toDTO)
        return ResponseEntity.ok(all)
    }
}