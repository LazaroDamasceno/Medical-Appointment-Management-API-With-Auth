package com.api.v2.medical_appointment

import com.api.v2.customers.Customer
import com.api.v2.doctors.Doctor
import com.api.v2.medical_appointment.enums.MedicalAppointmentStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "MedicalAppointments")
data class MedicalAppointment(
    @Id
    val id: String,
    val customer: Customer,
    val doctor: Doctor,
    val status: MedicalAppointmentStatus,
    val createdAt: LocalDateTime,
    val bookedAt: LocalDateTime,
    val cancelledAt: LocalDateTime?,
    val completedAt: LocalDateTime?
) {

    companion object {
        fun of (customer: Customer,
                doctor: Doctor,
                bookedAt: LocalDateTime
        ): MedicalAppointment {
            return MedicalAppointment(
                UUID.randomUUID().toString(),
                customer,
                doctor,
                MedicalAppointmentStatus.ACTIVE,
                LocalDateTime.now(),
                bookedAt,
                null,
                null
            )
        }
    }

    fun markAsCancelled(): MedicalAppointment {
        return MedicalAppointment(
            this.id,
            this.customer,
            this.doctor,
            MedicalAppointmentStatus.CANCELLED,
            this.createdAt,
            this.bookedAt,
            LocalDateTime.now(),
            null
        )
    }

    fun markAsCompleted(): MedicalAppointment {
        return MedicalAppointment(
            this.id,
            this.customer,
            this.doctor,
            MedicalAppointmentStatus.COMPLETED,
            this.createdAt,
            this.bookedAt,
            null,
            LocalDateTime.now()
        )
    }
}