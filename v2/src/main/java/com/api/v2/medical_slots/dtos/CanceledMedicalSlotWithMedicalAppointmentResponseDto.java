package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

public class CanceledMedicalSlotWithMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutDoctorResponseDto medicalAppointmentResponseDto;
    private String canceledAt;

    public CanceledMedicalSlotWithMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDtoWithoutDoctor(medicalSlot.getMedicalAppointment());
        this.canceledAt = "%s%s[%s]".formatted(
                medicalSlot.getCanceledAt(),
                medicalSlot.getCanceledAtZoneOffset(),
                medicalSlot.getCanceledAtZoneId()
        );
    }

    public static CanceledMedicalSlotWithMedicalAppointmentResponseDto from(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithMedicalAppointmentResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutDoctorResponseDto getMedicalAppointmentResponseDto() {
        return medicalAppointmentResponseDto;
    }

    public String getCanceledAt() {
        return canceledAt;
    }
}
