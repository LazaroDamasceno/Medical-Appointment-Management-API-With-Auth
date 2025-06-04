package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class ActiveMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private final MedicalSlotStatus status = MedicalSlotStatus.ACTIVE;

    private ActiveMedicalSlotResponseDTO(DoctorResponseDTO doctor, LocalDateTime availableAt) {
        super(doctor, availableAt);
    }

    public static ActiveMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new ActiveMedicalSlotResponseDTO(medicalSlot.toDTO().getDoctor(), medicalSlot.getAvailableAt());
    }
}
