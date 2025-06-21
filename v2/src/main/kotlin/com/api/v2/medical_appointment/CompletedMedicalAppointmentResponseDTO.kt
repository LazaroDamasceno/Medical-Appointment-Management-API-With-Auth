package com.api.v2.medical_appointment

import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_appointment.enums.MedicalAppointmentStatus
import java.time.LocalDateTime

class CompletedMedicalAppointmentResponseDTO(
    id: String,
    customer: CustomerResponseDTO,
    doctor: DoctorResponseDTO,
    status: MedicalAppointmentStatus,
    bookedAt: LocalDateTime,
    createdAt: LocalDateTime,
    completedAt: LocalDateTime
): MedicalAppointmentResponseDTO(id, customer, doctor, status,  bookedAt, createdAt)