package com.api.v2.medical_slots

import com.api.v2.doctors.Doctor
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document
class MedicalSlot private constructor(
    var doctor: Doctor,
    var availableAt: LocalDateTime
) {

    @Id
    var id: String = UUID.randomUUID().toString()
    var status: MedicalSlotStatus = MedicalSlotStatus.ACTIVE
    val createdAt: LocalDateTime = LocalDateTime.now()
    var canceledAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null

    companion object {
        fun of(doctor: Doctor, availableAt: LocalDateTime): MedicalSlot {
            return MedicalSlot(doctor, availableAt)
        }
    }

    fun markAsCompleted() {
        status = MedicalSlotStatus.COMPLETED
        canceledAt = LocalDateTime.now()
    }

    fun markAsCancelled() {
        status = MedicalSlotStatus.CANCELLED
        completedAt = LocalDateTime.now()
    }
}