package com.api.v1.medical_slots.response;

import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime completedAt;

    CompletedMedicalSlotResponseDto() {}

    private CompletedMedicalSlotResponseDto(String id,
                                            DoctorResponseDto doctor,
                                            LocalDateTime availableAt,
                                            MedicalSlotStatus status,
                                            LocalDateTime completedAt
    ) {
        super(id, doctor, availableAt, status);
        this.completedAt = completedAt;
    }

    public static CompletedMedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(
                medicalSlot.getId(),
                medicalSlot.getDoctor().toDto(),
                medicalSlot.getAvailableAt(),
                medicalSlot.getStatus(),
                medicalSlot.getCompletedAt()
        );
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
