package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CompletedMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime completedAt;
    private ZoneOffset completedAtZone;

    public CompletedMedicalAppointmentResponseDto() {
    }

    private CompletedMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = medicalAppointment.getCompletedAt();
        this.completedAtZone = medicalAppointment.getCompletedAtZone();
    }

    public static CompletedMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneOffset getCompletedAtZone() {
        return completedAtZone;
    }
}
