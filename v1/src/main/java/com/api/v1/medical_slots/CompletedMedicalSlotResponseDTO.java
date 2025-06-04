package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class CompletedMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final MedicalSlotStatus status = MedicalSlotStatus.COMPLETED;

    private CompletedMedicalSlotResponseDTO(DoctorResponseDTO doctor, LocalDateTime availableAt) {
        super(doctor, availableAt);
    }

    public static CompletedMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDTO(medicalSlot.toDTO().getDoctor(), medicalSlot.getAvailableAt());
    }
}
