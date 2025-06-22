package com.api.v2.medical_slots

import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_appointment.MedicalAppointment
import com.api.v2.medical_appointment.MedicalAppointmentResponseDTO
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import java.time.LocalDateTime

class CompletedDefaultMedicalSlotResponseDTO(
    id: String,
    status: MedicalSlotStatus,
    doctor: DoctorResponseDTO,
    availableAt: LocalDateTime,
    createdAt: LocalDateTime,
    completedAt: LocalDateTime,
    medicalAppointment: MedicalAppointmentResponseDTO
): DefaultMedicalSlotResponseDTO(id, status, doctor, availableAt, createdAt)