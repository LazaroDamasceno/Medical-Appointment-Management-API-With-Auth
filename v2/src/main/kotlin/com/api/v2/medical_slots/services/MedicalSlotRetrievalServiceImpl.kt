package com.api.v2.medical_slots.services

import com.api.v2.doctors.DoctorFinder
import com.api.v2.medical_slots.MedicalSlot
import com.api.v2.medical_slots.MedicalSlotFinder
import com.api.v2.medical_slots.MedicalSlotResponseDTO
import com.api.v2.medical_slots.controllers.MedicalSlotController
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.toDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MedicalSlotRetrievalServiceImpl(
    private val crudRepository: MedicalSlotCrudRepository,
    private val doctorFinder: DoctorFinder,
    private val medicalSlotFinder: MedicalSlotFinder
): MedicalSlotRetrievalService {

    override fun findByIdAndDoctor(
        id: String,
        medicalLicenseNumber: String
    ): ResponseEntity<MedicalSlotResponseDTO> {
        val foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber)
        val foundSlot = medicalSlotFinder.findByIdAndDoctor(id, foundDoctor)
        val dto = foundSlot.toDTO()
        dto.add(
            linkTo(methodOn(MedicalSlotController::class.java)
                .findByIdAndDoctor(id, medicalLicenseNumber)
            ).withSelfRel()
        )
        return ResponseEntity.ok(dto)
    }

    override fun findAllByDoctor(
        medicalLicenseNumber: String,
        pageable: Pageable
    ): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        val foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber)
        val page = crudRepository.findAllByDoctor(foundDoctor.id, pageable).map(MedicalSlot::toDTO)
        return ResponseEntity.ok(page)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        val page = crudRepository.findAll(pageable).map(MedicalSlot::toDTO)
        return ResponseEntity.ok(page)
    }
}