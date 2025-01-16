package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        this.completedAt = medicalSlot.getCanceledAt();
        this.completedAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CompletedMedicalSlotResponseDto create(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
