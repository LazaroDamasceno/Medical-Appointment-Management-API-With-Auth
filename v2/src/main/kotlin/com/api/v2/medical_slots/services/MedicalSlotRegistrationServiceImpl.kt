package com.api.v2.medical_slots.services

import com.api.v2.common.DuplicatedBookingDateTimeException
import com.api.v2.doctors.Doctor
import com.api.v2.doctors.DoctorFinder
import com.api.v2.medical_slots.MedicalSlot
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO
import com.api.v2.medical_slots.toDTO
import jakarta.validation.constraints.NotNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI
import java.time.LocalDateTime

@Service
class MedicalSlotRegistrationServiceImpl: MedicalSlotRegistrationService {

    private val crudRepository: MedicalSlotCrudRepository
    private val doctorFinder: DoctorFinder

    constructor(
        crudRepository: MedicalSlotCrudRepository,
        doctorFinder: DoctorFinder
    ) {
        this.crudRepository = crudRepository
        this.doctorFinder = doctorFinder
    }

    override fun register(
        doctorLicenseNumber: String,
        availableAt: @NotNull LocalDateTime
    ): ResponseEntity<MedicalSlotResponseDTO> {
        val foundDoctor = doctorFinder.findByLicenseNumber(doctorLicenseNumber)
        validate(foundDoctor, availableAt)
        val newSlot = MedicalSlot.of(foundDoctor, availableAt)
        val savedSlot = crudRepository.save(newSlot)
        val dto = savedSlot.toDTO()
        return ResponseEntity
            .created(URI.create("/api/v2/medical-slots"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
    }

    fun validate(doctor: Doctor, availableAt: @NotNull LocalDateTime) {
        if (crudRepository.findByDoctorAndAvailableAt(doctor.id, availableAt) != null) {
            throw DuplicatedBookingDateTimeException()
        }
    }
}