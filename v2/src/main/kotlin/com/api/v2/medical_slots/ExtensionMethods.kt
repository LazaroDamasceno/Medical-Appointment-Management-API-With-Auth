package com.api.v2.medical_slots

import com.api.v2.doctors.toDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus

fun MedicalSlot.toDTO(): MedicalSlotResponseDTO {
    if (this.status == MedicalSlotStatus.CANCELLED) {
        return CancelledMedicalSlotResponseDTO(
            this.id,
            this.doctor.toDTO(),
            this.availableAt,
            this.createdAt,
            this.cancelledAt!!
        )
    }
    else if (this.status == MedicalSlotStatus.COMPLETED) {
        return CompletedMedicalSlotResponseDTO(
            this.id,
            this.doctor.toDTO(),
            this.availableAt,
            this.createdAt,
            this.completedAt!!
        )
    }
    return MedicalSlotResponseDTO(
        this.id,
        this.doctor.toDTO(),
        this.availableAt,
        this.createdAt
    )
}