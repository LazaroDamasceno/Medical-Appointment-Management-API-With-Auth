package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

public class MedicalSlotWithMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutDoctorResponseDto medicalAppointment;

    public MedicalSlotWithMedicalAppointmentResponseDto() {
    }

    private MedicalSlotWithMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointment = MedicalAppointmentResponseMapper.mapToDtoWithoutDoctor(medicalSlot.getMedicalAppointment());
    }

    public static MedicalSlotWithMedicalAppointmentResponseDto from(MedicalSlot medicalSlot) {
        return new MedicalSlotWithMedicalAppointmentResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutDoctorResponseDto getMedicalAppointment() {
        return medicalAppointment;
    }
}
