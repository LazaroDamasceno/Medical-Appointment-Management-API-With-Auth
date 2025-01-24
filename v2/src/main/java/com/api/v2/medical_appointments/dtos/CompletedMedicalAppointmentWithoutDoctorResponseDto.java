package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalAppointmentWithoutDoctorResponseDto extends MedicalAppointmentWithoutDoctorResponseDto {

    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;

    public CompletedMedicalAppointmentWithoutDoctorResponseDto() {
    }

    private CompletedMedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = medicalAppointment.getCanceledAt();
        this.completedAtZoneId = medicalAppointment.getCanceledAtZoneId();
    }

    public static CompletedMedicalAppointmentWithoutDoctorResponseDto from(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentWithoutDoctorResponseDto(medicalAppointment);
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZoneId() {
        return completedAtZoneId;
    }
}
