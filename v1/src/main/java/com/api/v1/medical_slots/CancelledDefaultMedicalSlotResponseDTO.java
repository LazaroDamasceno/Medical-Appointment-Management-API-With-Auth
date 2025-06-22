package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CancelledDefaultMedicalSlotResponseDTO extends DefaultMedicalSlotResponseDTO {

    private final LocalDateTime cancelledAt;

    CancelledDefaultMedicalSlotResponseDTO(String id,
                                           MedicalSlotStatus status,
                                           DoctorResponseDTO doctor,
                                           LocalDateTime availableAt,
                                           LocalDateTime createdAt,
                                           LocalDateTime cancelledAt
    ) {
        super(id, status, doctor, availableAt, createdAt);
        this.cancelledAt = cancelledAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
