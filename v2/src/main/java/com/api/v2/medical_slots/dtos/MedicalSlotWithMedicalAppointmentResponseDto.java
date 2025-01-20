package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutCustomerAndDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

public class MedicalSlotWithMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutCustomerAndDoctorResponseDto completeMedicalAppointmentResponseDto;

    public MedicalSlotWithMedicalAppointmentResponseDto() {
    }

    private MedicalSlotWithMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.completeMedicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment());
    }

    public static MedicalSlotWithMedicalAppointmentResponseDto create(MedicalSlot medicalSlot) {
        return new MedicalSlotWithMedicalAppointmentResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutCustomerAndDoctorResponseDto getCompleteMedicalAppointmentResponseDto() {
        return completeMedicalAppointmentResponseDto;
    }
}
