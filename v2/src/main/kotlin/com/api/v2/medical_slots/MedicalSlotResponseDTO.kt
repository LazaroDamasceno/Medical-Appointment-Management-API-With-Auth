package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

sealed class MedicalSlotResponseDTO(
    val id: String,
    val doctor: DoctorResponseDTO,
    val availableAt: LocalDateTime,
    val createdAt: LocalDateTime
): RepresentationModel<MedicalSlotResponseDTO>()