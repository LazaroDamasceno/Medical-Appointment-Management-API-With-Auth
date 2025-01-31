package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

public class CompletedMedicalAppointmentWithoutDoctorResponseDto extends MedicalAppointmentWithoutDoctorResponseDto {

    private String completedAt;

    public CompletedMedicalAppointmentWithoutDoctorResponseDto() {
    }

    private CompletedMedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = "%s%s[%s]".formatted(
                medicalAppointment.getCompletedAt(),
                medicalAppointment.getCompletedAtZoneOffset(),
                medicalAppointment.getCompletedAtZoneId()
        );
    }

    public static CompletedMedicalAppointmentWithoutDoctorResponseDto from(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentWithoutDoctorResponseDto(medicalAppointment);
    }

    public String getCompletedAt() {
        return completedAt;
    }
}
