package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CompletedMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final LocalDateTime completedAt;

    public CompletedMedicalSlotResponseDTO(MedicalSlotStatus status,
                                           DoctorResponseDTO doctor,
                                           LocalDateTime availableAt,
                                           LocalDateTime completedAt
    ) {
        super(status, doctor, availableAt);
        this.completedAt = completedAt;
    }

    public static CompletedMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDTO(
                MedicalSlotStatus.COMPLETED,
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getCompletedAt()
        );
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
