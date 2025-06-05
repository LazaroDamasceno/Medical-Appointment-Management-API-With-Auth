package com.api.v2.medical_slots

import com.api.v2.doctors.Doctor
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "MedicalSlots")
class MedicalSlot private constructor(
    var doctor: Doctor,
    val availableAt: LocalDateTime
) {

    @Id
    @Indexed(unique = true)
    var id: String = UUID.randomUUID().toString()
    var status: MedicalSlotStatus = MedicalSlotStatus.ACTIVE
    val createdAt: LocalDateTime = LocalDateTime.now()
    var cancelledAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null

    companion object {
        fun of(doctor: Doctor, availableAt: LocalDateTime): MedicalSlot {
            return MedicalSlot(doctor, availableAt)
        }
    }

    fun markAsCancelled() {
        cancelledAt = LocalDateTime.now()
        status = MedicalSlotStatus.CANCELLED
    }

    fun markAsCompleted() {
        completedAt = LocalDateTime.now()
        status = MedicalSlotStatus.COMPLETED
    }
}