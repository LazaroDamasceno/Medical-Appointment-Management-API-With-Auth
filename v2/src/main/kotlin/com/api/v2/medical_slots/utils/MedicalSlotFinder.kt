package com.api.v2.medical_slots.utils

import com.api.v2.medical_slots.domain.MedicalSlot

interface MedicalSlotFinder {
    fun findById(id: String): MedicalSlot
}