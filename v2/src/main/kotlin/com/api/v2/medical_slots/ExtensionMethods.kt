package com.api.v2.medical_slots

import com.api.v2.doctors.toDTO
import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO

fun MedicalSlot.toDTO(): MedicalSlotResponseDTO {
    return MedicalSlotResponseDTO(
        this.doctor.toDTO(),
        this.availableAt
    )
}