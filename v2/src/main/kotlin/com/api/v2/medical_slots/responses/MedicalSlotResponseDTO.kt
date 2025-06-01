package com.api.v2.medical_slots.responses

import com.api.v2.doctors.responses.DoctorResponseDTO
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

class MedicalSlotResponseDTO(
    val doctor: DoctorResponseDTO,
    val availableAt: LocalDateTime
): RepresentationModel<MedicalSlotResponseDTO>()