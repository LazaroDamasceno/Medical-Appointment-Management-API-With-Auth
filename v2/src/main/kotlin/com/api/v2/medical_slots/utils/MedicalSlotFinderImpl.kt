package com.api.v2.medical_slots.utils

import com.api.v2.doctors.Doctor
import com.api.v2.medical_slots.MedicalSlot
import com.api.v2.medical_slots.MedicalSlotFinder
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import com.api.v2.medical_slots.exceptions.InaccessibleMedicalSlotException
import com.api.v2.medical_slots.exceptions.MedicalSlotNotFoundException
import org.springframework.stereotype.Service

@Service
class MedicalSlotFinderImpl(
    private val crudRepository: MedicalSlotCrudRepository
): MedicalSlotFinder {

    override fun findById(id: String): MedicalSlot {
        val foundSlot = crudRepository.findById(id)
        if (foundSlot.isEmpty) {
            throw MedicalSlotNotFoundException(id)
        }
        return foundSlot.get()
    }

    override fun findByIdAndDoctor(id: String, doctor: Doctor): MedicalSlot {
        val foundSlot = findById(id)
        if (foundSlot.doctor.id != doctor.id) {
            throw InaccessibleMedicalSlotException(id, doctor.licenseNumber)
        }
        return foundSlot
    }
}