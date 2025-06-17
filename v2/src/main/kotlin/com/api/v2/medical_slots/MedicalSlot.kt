package com.api.v2.medical_slots

import com.api.v2.doctors.Doctor
import com.api.v2.medical_slots.enums.MedicalSlotStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "MedicalSlots")
data class MedicalSlot(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val doctor: Doctor,
    val availableAt: LocalDateTime,
    val status: MedicalSlotStatus,
    val createdAt: LocalDateTime,
    val cancelledAt: LocalDateTime?,
    val completedAt: LocalDateTime?
) {

    companion object {
        fun of(doctor: Doctor, availableAt: LocalDateTime): MedicalSlot {
            return MedicalSlot(
                UUID.randomUUID().toString(),
                doctor,
                availableAt,
                MedicalSlotStatus.ACTIVE,
                LocalDateTime.now(),
                null,
                null
            )
        }
    }

    fun markAsCancelled(): MedicalSlot {
        return MedicalSlot(
            this.id,
            this.doctor,
            this.availableAt,
            MedicalSlotStatus.CANCELLED,
            this.createdAt,
            LocalDateTime.now(),
            null
        )
    }

    fun markAsCompleted(): MedicalSlot {
        return MedicalSlot(
            this.id,
            this.doctor,
            this.availableAt,
            MedicalSlotStatus.COMPLETED,
            this.createdAt,
            null,
            LocalDateTime.now()
        )
    }
}