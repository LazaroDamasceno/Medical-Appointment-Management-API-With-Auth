package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import java.time.LocalDateTime

class CompletedMedicalSlotResponseDTO(
    id: String,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    completedAt: LocalDateTime
): MedicalSlotResponseDTO(id, doctor, availableAt, createdAt)