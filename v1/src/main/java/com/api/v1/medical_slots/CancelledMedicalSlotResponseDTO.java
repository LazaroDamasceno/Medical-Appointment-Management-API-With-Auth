package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CancelledMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final LocalDateTime cancelledAt;

    private CancelledMedicalSlotResponseDTO(String id,
                                           MedicalSlotStatus status,
                                           DoctorResponseDTO doctor,
                                           LocalDateTime availableAt,
                                           LocalDateTime createdAt,
                                           LocalDateTime cancelledAt
    ) {
        super(id, status, doctor, availableAt, createdAt);
        this.cancelledAt = cancelledAt;
    }

    public static CancelledMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CancelledMedicalSlotResponseDTO(
                medicalSlot.getId(),
                MedicalSlotStatus.CANCELLED,
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getCreatedAt(),
                medicalSlot.getCancelledAt()
        );
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
