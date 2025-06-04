package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class ActiveMedicalSlotResponseDTO extends MedicalSlotResponseDTO {

    private ActiveMedicalSlotResponseDTO(MedicalSlotStatus status,
                                         DoctorResponseDTO doctor,
                                         LocalDateTime availableAt
    ) {
        super(status, doctor, availableAt);
    }

    public static ActiveMedicalSlotResponseDTO from(MedicalSlot medicalSlot) {
        return new ActiveMedicalSlotResponseDTO(
                MedicalSlotStatus.ACTIVE,
                medicalSlot.toDTO().getDoctor(),
                medicalSlot.getAvailableAt()
        );
    }
}
