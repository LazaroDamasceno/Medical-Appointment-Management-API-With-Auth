package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_slots.domain.MedicalSlot;

public class CanceledMedicalSlotWithoutMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private String canceledAt;

    public CanceledMedicalSlotWithoutMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.canceledAt = "%s%s[%s]".formatted(
                medicalSlot.getCanceledAt(),
                medicalSlot.getCanceledAtZoneOffset(),
                medicalSlot.getCanceledAtZoneId()
        );
    }

    public static CanceledMedicalSlotWithoutMedicalAppointmentResponseDto from(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithoutMedicalAppointmentResponseDto(medicalSlot);
    }

    public String getCanceledAt() {
        return canceledAt;
    }

}
