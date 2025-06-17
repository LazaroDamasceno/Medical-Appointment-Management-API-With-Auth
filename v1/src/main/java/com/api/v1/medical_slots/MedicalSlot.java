package com.api.v1.medical_slots;

import com.api.v1.doctors.Doctor;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "MedicalSlots")
public record MedicalSlot(

    @Id
    String id,
    Doctor doctor,
    MedicalSlotStatus status,
    LocalDateTime availableAt,
    LocalDateTime createdAt,
    LocalDateTime cancelledAt,
    LocalDateTime completedAt
) {
    public static MedicalSlot of(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(
                UUID.randomUUID().toString(),
                doctor,
                MedicalSlotStatus.ACTIVE,
                availableAt,
                LocalDateTime.now(),
                null,
                null
        );
    }

    public MedicalSlot markAsCancelled() {
        return new MedicalSlot(
                this.id,
                this.doctor,
                MedicalSlotStatus.CANCELLED,
                this.availableAt,
                this.createdAt,
                LocalDateTime.now(),
                null
        );
    }

    public MedicalSlot markAsCompleted() {
        return new MedicalSlot(
                this.id,
                this.doctor,
                MedicalSlotStatus.COMPLETED,
                this.availableAt,
                this.createdAt,
                null,
                LocalDateTime.now()
        );
    }

    public MedicalSlotResponseDTO toDTO() {
        if (status.equals(MedicalSlotStatus.CANCELLED)) {
            CancelledMedicalSlotResponseDTO.from(this);
        }
        else if (status.equals(MedicalSlotStatus.COMPLETED)) {
            CompletedMedicalSlotResponseDTO.from(this);
        }
        return MedicalSlotResponseDTO.from(this);
    }
}
