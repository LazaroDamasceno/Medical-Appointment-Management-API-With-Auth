package com.api.v2.medical_appointment

import com.api.v2.customers.toDTO
import com.api.v2.doctors.toDTO
import com.api.v2.medical_appointment.enums.MedicalAppointmentStatus

fun MedicalAppointment.toDTO(): MedicalAppointmentResponseDTO {
    if (this.status == MedicalAppointmentStatus.CANCELLED) {
        return MedicalAppointmentResponseDTO(
            this.id,
            this.customer.toDTO(),
            this.doctor.toDTO(),
            MedicalAppointmentStatus.CANCELLED,
            this.bookedAt,
            this.createdAt
        )
    }
    else if(this.status == MedicalAppointmentStatus.COMPLETED) {
        return MedicalAppointmentResponseDTO(
            this.id,
            this.customer.toDTO(),
            this.doctor.toDTO(),
            MedicalAppointmentStatus.COMPLETED,
            this.bookedAt,
            this.createdAt
        )
    }
    return MedicalAppointmentResponseDTO(
        this.id,
        this.customer.toDTO(),
        this.doctor.toDTO(),
        MedicalAppointmentStatus.ACTIVE,
        this.bookedAt,
        this.createdAt
    )
}