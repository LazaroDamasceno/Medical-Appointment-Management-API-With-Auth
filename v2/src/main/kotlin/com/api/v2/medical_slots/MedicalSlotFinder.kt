package com.api.v2.medical_slots

import com.api.v2.doctors.Doctor

interface MedicalSlotFinder {
    fun findById(id: String): MedicalSlot
    fun findByIdAndDoctor(id: String, doctor: Doctor): MedicalSlot
}