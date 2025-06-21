package com.api.v2.medical_appointment

import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.medical_appointment.enums.MedicalAppointmentStatus
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

open class MedicalAppointmentResponseDTO(
    val id: String,
    val customer: CustomerResponseDTO,
    val doctor: DoctorResponseDTO,
    val status: MedicalAppointmentStatus,
    val bookedAt: LocalDateTime,
    val createdAt: LocalDateTime
): RepresentationModel<MedicalAppointmentResponseDTO>()
