package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import java.time.LocalDateTime

class CancelledMedicalSlotResponseDTO(
    id: String,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    cancelledAt: LocalDateTime
) : MedicalSlotResponseDTO(id, doctor, availableAt, createdAt)