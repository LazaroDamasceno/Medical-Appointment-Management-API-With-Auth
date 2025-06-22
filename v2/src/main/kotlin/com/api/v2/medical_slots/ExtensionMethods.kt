package com.api.v2.medical_slots

import com.api.v2.doctors.toDTO
import com.api.v2.medical_appointment.toDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus

fun MedicalSlot.toDTO(): DefaultMedicalSlotResponseDTO {
    if (this.status == MedicalSlotStatus.CANCELLED) {
        return CancelledDefaultMedicalSlotResponseDTO(
            this.id,
            this.status,
            this.doctor.toDTO(),
            this.availableAt,
            this.createdAt,
            this.cancelledAt!!
        )
    }
    else if (this.status == MedicalSlotStatus.COMPLETED) {
        return CompletedDefaultMedicalSlotResponseDTO(
            this.id,
            this.status,
            this.doctor.toDTO(),
            this.availableAt,
            this.createdAt,
            this.completedAt!!,
            this.medicalAppointment!!.toDTO()
        )
    }
    else if (this.status == MedicalSlotStatus.ACTIVE
        && this.medicalAppointment != null
    ) {
        return MedicalSlotWithAppointmentResponseDTO(
            this.id,
            this.status,
            this.doctor.toDTO(),
            this.availableAt,
            this.createdAt,
            this.medicalAppointment.toDTO()
        )
    }
    return DefaultMedicalSlotResponseDTO(
        this.id,
        this.status,
        this.doctor.toDTO(),
        this.availableAt,
        this.createdAt
    )
}