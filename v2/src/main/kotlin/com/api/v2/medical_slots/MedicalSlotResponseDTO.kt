package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

open class MedicalSlotResponseDTO(
    val id: String,
    val status: MedicalSlotStatus,
    val doctor: DoctorResponseDTO,
    val availableAt: LocalDateTime,
    val createdAt: LocalDateTime
): RepresentationModel<MedicalSlotResponseDTO>()