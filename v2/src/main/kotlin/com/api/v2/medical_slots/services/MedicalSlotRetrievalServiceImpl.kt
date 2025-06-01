package com.api.v2.medical_slots.services

import com.api.v2.doctors.DoctorFinder
import com.api.v2.medical_slots.InaccessibleMedicalSlotException
import com.api.v2.medical_slots.MedicalSlot
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import com.api.v2.medical_slots.toDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
        return ResponseEntity.ok(dto)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<Page<MedicalSlotResponseDTO>> {
        val all = crudRepository
            .findAll(pageable)
            .map(MedicalSlot::toDTO)
        return ResponseEntity.ok(all)
    }
}