package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutDoctorResponseDto medicalAppointment;
    private String completedAt;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointment = MedicalAppointmentResponseMapper.mapToDtoWithoutDoctor(medicalSlot.getMedicalAppointment());
        this.completedAt = "%s%s[%s]".formatted(
                medicalSlot.getCompletedAt(),
                medicalSlot.getCompletedAtZoneOffset(),
                medicalSlot.getCompletedAtZoneId()
        );
    }

    public static CompletedMedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutDoctorResponseDto getMedicalAppointment() {
        return medicalAppointment;
    }

    public String getCompletedAt() {
        return completedAt;
    }

}
