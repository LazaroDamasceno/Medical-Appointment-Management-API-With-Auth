package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CancelledMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final MedicalSlotStatus status = MedicalSlotStatus.CANCELLED;

    private CancelledMedicalSlotResponseDTO(DoctorResponseDTO doctor, LocalDateTime availableAt) {
        super(doctor, availableAt);
    }

    public static CancelledMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CancelledMedicalSlotResponseDTO(medicalSlot.toDTO().getDoctor(), medicalSlot.getAvailableAt());
    }
}
