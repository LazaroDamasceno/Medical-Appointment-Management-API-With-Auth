package com.api.v2.medical_slots.utils

import com.api.v2.doctors.utils.toDTO
import com.api.v2.medical_slots.domain.MedicalSlot
import com.api.v2.medical_slots.responses.MedicalSlotResponseDTO

fun MedicalSlot.toDTO(): MedicalSlotResponseDTO {
    return MedicalSlotResponseDTO(
        this.doctor.toDTO(),
        this.availableAt
    )
}