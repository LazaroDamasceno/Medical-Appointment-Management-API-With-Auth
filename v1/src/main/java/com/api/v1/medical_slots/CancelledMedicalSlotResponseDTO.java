package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CancelledMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final LocalDateTime cancelledAt;

    public CancelledMedicalSlotResponseDTO(MedicalSlotStatus status,
                                           DoctorResponseDTO doctor,
                                           LocalDateTime availableAt,
                                           LocalDateTime cancelledAt) {
        super(status, doctor, availableAt);
        this.cancelledAt = cancelledAt;
    }

    public static CancelledMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CancelledMedicalSlotResponseDTO(
                MedicalSlotStatus.CANCELLED,
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getCancelledAt()
        );
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
