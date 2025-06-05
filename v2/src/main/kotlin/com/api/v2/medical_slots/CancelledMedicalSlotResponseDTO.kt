package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import java.time.LocalDateTime

class CancelledMedicalSlotResponseDTO(
    id: String,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    cancelledAt: LocalDateTime
) : MedicalSlotResponseDTO(id, MedicalSlotStatus.CANCELLED, doctor, availableAt, createdAt)