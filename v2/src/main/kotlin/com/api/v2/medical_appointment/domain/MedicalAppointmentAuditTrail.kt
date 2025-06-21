package com.api.v2.medical_appointment.domain

import com.api.v2.medical_appointment.MedicalAppointment
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "MedicalAppointmentAuditTrail")
data class MedicalAppointmentAuditTrail(
    @Id
    val id: String,
    val medicalAppointment: MedicalAppointment,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of(medicalAppointment: MedicalAppointment): MedicalAppointmentAuditTrail {
            return MedicalAppointmentAuditTrail(
                UUID.randomUUID().toString(),
                medicalAppointment,
                LocalDateTime.now()
            )
        }
    }
}
