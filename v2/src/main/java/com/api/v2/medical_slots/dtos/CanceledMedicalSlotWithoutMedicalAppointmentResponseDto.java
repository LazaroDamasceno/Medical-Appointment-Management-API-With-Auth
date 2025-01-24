package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalSlotWithoutMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;

    public CanceledMedicalSlotWithoutMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.canceledAt = medicalSlot.getCanceledAt();
        this.canceledAtZoneId = medicalSlot.getCanceledAtZoneId();
    }

    public static CanceledMedicalSlotWithoutMedicalAppointmentResponseDto from(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(medicalSlot);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZoneId() {
        return canceledAtZoneId;
    }
}
