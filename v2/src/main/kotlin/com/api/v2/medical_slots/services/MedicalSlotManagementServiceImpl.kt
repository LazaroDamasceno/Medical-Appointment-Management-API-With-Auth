package com.api.v2.medical_slots.services

import com.api.v2.doctors.DoctorFinder
import com.api.v2.medical_slots.MedicalSlot
import com.api.v2.medical_slots.MedicalSlotFinder
import com.api.v2.medical_slots.domain.MedicalSlotAuditRepository
import com.api.v2.medical_slots.domain.MedicalSlotAuditTrail
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import com.api.v2.medical_slots.exceptions.CancelledMedicalSlotException
import com.api.v2.medical_slots.exceptions.CompletedMedicalSlotException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MedicalSlotManagementServiceImpl(
    private val crudRepository: MedicalSlotCrudRepository,
    private val auditRepository: MedicalSlotAuditRepository,
    private val doctorFinder: DoctorFinder,
    private val medicalSlotFinder: MedicalSlotFinder
): MedicalSlotManagementService {

    override fun cancel(medicalLicenseNumber: String, slotId: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber)
        val foundSlot = medicalSlotFinder.findByIdAndDoctor(slotId, foundDoctor)
        validate(foundSlot)
        val auditTrail = MedicalSlotAuditTrail.of(foundSlot)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedSlot = foundSlot.markAsCancelled()
        val savedSlot = crudRepository.save(updatedSlot)
        return ResponseEntity.noContent().build()
    }

    override fun complete(medicalLicenseNumber: String, slotId: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber)
        val foundSlot = medicalSlotFinder.findByIdAndDoctor(slotId, foundDoctor)
        validate(foundSlot)
        val auditTrail = MedicalSlotAuditTrail.of(foundSlot)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedSlot = foundSlot.markAsCompleted()
        val savedSlot = crudRepository.save(updatedSlot)
        return ResponseEntity.noContent().build()
    }

    fun validate(medicalSlot: MedicalSlot) {
        if (medicalSlot.status == MedicalSlotStatus.CANCELLED) {
            throw CancelledMedicalSlotException(medicalSlot.id)
        }
        else if (medicalSlot.status == MedicalSlotStatus.COMPLETED) {
            throw CompletedMedicalSlotException(medicalSlot.id)
        }
    }
}