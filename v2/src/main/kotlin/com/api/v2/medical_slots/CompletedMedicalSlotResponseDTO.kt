package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import java.time.LocalDateTime

class CompletedMedicalSlotResponseDTO(
    id: String,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    completedAt: LocalDateTime
): MedicalSlotResponseDTO(id, MedicalSlotStatus.COMPLETED, doctor, availableAt, createdAt)