package com.api.v1.medical_slots.response;

import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;


import java.time.LocalDateTime;

public final class CanceledMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime canceledAt;

    private CanceledMedicalSlotResponseDto() {}

    private CanceledMedicalSlotResponseDto(String id,
                                          DoctorResponseDto doctor,
                                          LocalDateTime availableAt,
                                          MedicalSlotStatus status,
                                          LocalDateTime canceledAt
    ) {
        super(id, doctor, availableAt, status);
        this.canceledAt = canceledAt;
    }

    public static CanceledMedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotResponseDto(
                medicalSlot.getId(),
                medicalSlot.getDoctor().toDto(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getStatus(),
                medicalSlot.getCanceledAt()
        );
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }
}
