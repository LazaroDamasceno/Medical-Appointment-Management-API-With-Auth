package com.api.v2.medical_slots

interface MedicalSlotFinder {
    fun findById(id: String): MedicalSlot
}