package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutDoctorResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentWithoutDoctorResponseDto medicalAppointment;
    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointment = MedicalAppointmentResponseMapper.mapToDtoWithoutDoctor(medicalSlot.getMedicalAppointment());
        this.completedAt = medicalSlot.getCanceledAt();
        this.completedAtZoneId = medicalSlot.getCanceledAtZoneId();
    }

    public static CompletedMedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public MedicalAppointmentWithoutDoctorResponseDto getMedicalAppointment() {
        return medicalAppointment;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZoneId() {
        return completedAtZoneId;
    }
}
