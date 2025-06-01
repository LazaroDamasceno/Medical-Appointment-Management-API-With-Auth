package com.api.v2.medical_slots.utils

import com.api.v2.medical_slots.domain.MedicalSlot
import com.api.v2.medical_slots.exceptions.MedicalSlotNotFoundException
import com.api.v2.medical_slots.domain.MedicalSlotCrudRepository
import org.springframework.stereotype.Service

@Service
class MedicalSlotFinderImpl: MedicalSlotFinder {

    private val crudRepository: MedicalSlotCrudRepository

    constructor(crudRepository: MedicalSlotCrudRepository) {
        this.crudRepository = crudRepository
    }

    override fun findById(id: String): MedicalSlot {
        val foundSlot = crudRepository.findById(id)
        if (foundSlot.isEmpty) {
            throw MedicalSlotNotFoundException(id)
        }
        return foundSlot.get()
    }
}