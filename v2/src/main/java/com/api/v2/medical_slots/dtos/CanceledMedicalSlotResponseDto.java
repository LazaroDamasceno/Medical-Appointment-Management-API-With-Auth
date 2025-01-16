package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledMedicalSlotResponseDto() {
    }

    private CanceledMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        this.canceledAt = medicalSlot.getCanceledAt();
        this.canceledAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CanceledMedicalSlotResponseDto create(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotResponseDto(medicalSlot);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
