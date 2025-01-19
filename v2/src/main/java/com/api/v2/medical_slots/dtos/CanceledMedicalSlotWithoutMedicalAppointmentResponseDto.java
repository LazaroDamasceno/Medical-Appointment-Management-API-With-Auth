package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CanceledMedicalSlotWithoutMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime canceledAt;
    private ZoneOffset canceledAtZone;

    public CanceledMedicalSlotWithoutMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.canceledAt = medicalSlot.getCanceledAt();
        this.canceledAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CanceledMedicalSlotWithoutMedicalAppointmentResponseDto create(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(medicalSlot);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneOffset getCanceledAtZone() {
        return canceledAtZone;
    }
}
