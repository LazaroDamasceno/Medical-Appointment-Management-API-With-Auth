package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedCompleteMedicalAppointmentResponseDto extends CompleteMedicalAppointmentResponseDto {

    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    public CompletedCompleteMedicalAppointmentResponseDto() {
    }

    private CompletedCompleteMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = medicalAppointment.getCompletedAt();
        this.completedAtZone = medicalAppointment.getCompletedAtZone();
    }

    public static CompletedCompleteMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CompletedCompleteMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
