package com.api.v2.medical_appointment

import com.api.v2.customers.toDTO
import com.api.v2.doctors.toDTO

fun MedicalAppointment.toDTO(): MedicalAppointmentResponseDTO {
    return MedicalAppointmentResponseDTO(
        this.id,
        this.customer.toDTO(),
        this.doctor.toDTO(),
        this.status,
        this.bookedAt,
        this.createdAt
    )
}