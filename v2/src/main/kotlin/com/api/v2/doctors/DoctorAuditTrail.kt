package com.api.v2.doctors

import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

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
