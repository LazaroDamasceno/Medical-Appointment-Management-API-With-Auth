package com.api.v2.doctors.domain

import com.api.v2.doctors.Doctor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "DoctorAuditTrail")
data class DoctorAuditTrail(
    @Id
    val id: String,
    val doctor: Doctor,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of(doctor: Doctor): DoctorAuditTrail {
            return DoctorAuditTrail(
                UUID.randomUUID().toString(),
                doctor,
                LocalDateTime.now()
            )
        }
    }
}
