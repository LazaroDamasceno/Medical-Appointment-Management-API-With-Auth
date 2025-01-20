package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private CompleteMedicalAppointmentResponseDto completeMedicalAppointmentResponseDto;
    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.completeMedicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment());
        this.completedAt = medicalSlot.getCanceledAt();
        this.completedAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CompletedMedicalSlotResponseDto create(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public CompleteMedicalAppointmentResponseDto getMedicalAppointmentResponseDto() {
        return completeMedicalAppointmentResponseDto;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
