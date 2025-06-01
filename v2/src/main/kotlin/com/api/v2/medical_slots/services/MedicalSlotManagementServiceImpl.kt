package com.api.v2.medical_slots.services

import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.utils.DoctorFinder
import com.api.v2.medical_slots.exceptions.CancelledMedicalSlotException
import com.api.v2.medical_slots.exceptions.CompletedMedicalSlotException
import com.api.v2.medical_slots.exceptions.InaccessibleMedicalSlotException
import com.api.v2.medical_slots.domain.MedicalSlot
import com.api.v2.medical_slots.utils.MedicalSlotFinder
import com.api.v2.medical_slots.domain.MedicalSlotAuditRepository
import com.api.v2.medical_slots.domain.MedicalSlotAuditTrail
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MedicalSlotManagementServiceImpl: MedicalSlotManagementService {

    private val crudRepository: MedicalSlotCrudRepository
    private val auditRepository: MedicalSlotAuditRepository
    private val doctorFinder: DoctorFinder
    private val slotFinder: MedicalSlotFinder

    constructor(
        crudRepository: MedicalSlotCrudRepository,
        auditRepository: MedicalSlotAuditRepository,
        doctorFinder: DoctorFinder,
        slotFinder: MedicalSlotFinder
    ) {
        this.crudRepository = crudRepository
        this.auditRepository = auditRepository
        this.doctorFinder = doctorFinder
        this.slotFinder = slotFinder
    }

    override fun cancel(doctorLicenseNumber: String, id: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(doctorLicenseNumber)
        val foundSlot = slotFinder.findById(id)
        validate(foundDoctor, id, foundSlot)
        val auditTrail = MedicalSlotAuditTrail.of(foundSlot)
        val savedAuditTrail = auditRepository.save(auditTrail)
        foundSlot.markAsCancelled()
        val updatedSlot = crudRepository.save(foundSlot)
        return ResponseEntity.noContent().build()
    }

    override fun complete(doctorLicenseNumber: String, id: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(doctorLicenseNumber)
        val foundSlot = slotFinder.findById(id)
        validate(foundDoctor, id, foundSlot)
        val auditTrail = MedicalSlotAuditTrail.of(foundSlot)
        val savedAuditTrail = auditRepository.save(auditTrail)
        foundSlot.markAsCompleted()
        val updatedSlot = crudRepository.save(foundSlot)
        return ResponseEntity.noContent().build()
    }

    fun validate(doctor: Doctor, id: String, medicalSlot: MedicalSlot) {
        if (medicalSlot.doctor.id != doctor.id) {
            throw InaccessibleMedicalSlotException(doctor.licenseNumber)
        }
        if (crudRepository.findCancelledByDoctorAndId(id, doctor.id) != null) {
            throw CancelledMedicalSlotException(id)
        }
        if (crudRepository.findCompletedByDoctorAndId(id, doctor.id) != null) {
            throw CompletedMedicalSlotException(id)
        }
    }
}