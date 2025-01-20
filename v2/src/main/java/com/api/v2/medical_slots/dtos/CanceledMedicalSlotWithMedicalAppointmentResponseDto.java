package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutCustomerAndDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalSlotWithMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutCustomerAndDoctorResponseDto completeMedicalAppointmentResponseDto;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledMedicalSlotWithMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.completeMedicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment());
        this.canceledAt = medicalSlot.getCanceledAt();
        this.canceledAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CanceledMedicalSlotWithMedicalAppointmentResponseDto create(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithMedicalAppointmentResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutCustomerAndDoctorResponseDto getCompleteMedicalAppointmentResponseDto() {
        return completeMedicalAppointmentResponseDto;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
