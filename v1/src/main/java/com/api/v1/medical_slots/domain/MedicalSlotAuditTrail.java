package com.api.v1.medical_slots.domain;

import com.api.v1.medical_slots.MedicalSlot;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "MedicalSlotAuditTrail")
public record MedicalSlotAuditTrail(
        @Id
        String id,
        MedicalSlot medicalSlot,
        LocalDateTime createdAt
) {

    public static MedicalSlotAuditTrail of(MedicalSlot medicalSlot) {
        return new MedicalSlotAuditTrail(
                UUID.randomUUID().toString(),
                medicalSlot,
                LocalDateTime.now()
        );
    }
}
