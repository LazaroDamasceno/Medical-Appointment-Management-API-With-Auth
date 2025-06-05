package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CompletedMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final LocalDateTime completedAt;

    private CompletedMedicalSlotResponseDTO(String id,
                                           MedicalSlotStatus status,
                                           DoctorResponseDTO doctor,
                                           LocalDateTime availableAt,
                                           LocalDateTime createdAt,
                                           LocalDateTime completedAt
    ) {
        super(id, status, doctor, availableAt, createdAt);
        this.completedAt = completedAt;
    }

    public static CompletedMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDTO(
                medicalSlot.getId(),
                MedicalSlotStatus.COMPLETED,
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getCreatedAt(),
                medicalSlot.getCompletedAt()
        );
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
