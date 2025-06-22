package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import java.time.LocalDateTime

class CancelledDefaultMedicalSlotResponseDTO(
    id: String,
    status: MedicalSlotStatus,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    cancelledAt: LocalDateTime
) : DefaultMedicalSlotResponseDTO(id, status, doctor, availableAt, createdAt)