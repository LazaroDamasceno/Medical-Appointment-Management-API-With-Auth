package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

public class CompletedMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private String completedAt;

    public CompletedMedicalAppointmentResponseDto() {
    }

    private CompletedMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = "%s%s[%s]".formatted(
                medicalAppointment.getCompletedAt(),
                medicalAppointment.getCompletedAtZoneOffset(),
                medicalAppointment.getCompletedAtZoneId()
        );
    }

    public static CompletedMedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentResponseDto(medicalAppointment);
    }

    public String getCompletedAt() {
        return completedAt;
    }
}
