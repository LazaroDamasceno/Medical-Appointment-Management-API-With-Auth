package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentResponseDto medicalAppointmentResponseDto;
    private LocalDateTime completedAt;
    private ZoneOffset completedAtZone;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment());
        this.completedAt = medicalSlot.getCanceledAt();
        this.completedAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CompletedMedicalSlotResponseDto create(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public MedicalAppointmentResponseDto getMedicalAppointmentResponseDto() {
        return medicalAppointmentResponseDto;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneOffset getCompletedAtZone() {
        return completedAtZone;
    }
}
