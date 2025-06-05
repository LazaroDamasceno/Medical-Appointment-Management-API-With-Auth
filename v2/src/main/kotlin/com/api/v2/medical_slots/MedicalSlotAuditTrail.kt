package com.api.v2.medical_slots

import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

data class MedicalSlotAuditTrail(
    @Id
    val id: String,
    val medicalSlot: MedicalSlot,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of(medicalSlot: MedicalSlot): MedicalSlotAuditTrail {
            return MedicalSlotAuditTrail(
                UUID.randomUUID().toString(),
                medicalSlot,
                LocalDateTime.now()
            )
        }
    }
}
