package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalSlotWithMedicalAppointmentResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutDoctorResponseDto medicalAppointmentResponseDto;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledMedicalSlotWithMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalSlotWithMedicalAppointmentResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDtoWithoutDoctor(medicalSlot.getMedicalAppointment());
        this.canceledAt = medicalSlot.getCanceledAt();
        this.canceledAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CanceledMedicalSlotWithMedicalAppointmentResponseDto from(MedicalSlot medicalSlot) {
        return new CanceledMedicalSlotWithMedicalAppointmentResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutDoctorResponseDto getMedicalAppointmentResponseDto() {
        return medicalAppointmentResponseDto;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
