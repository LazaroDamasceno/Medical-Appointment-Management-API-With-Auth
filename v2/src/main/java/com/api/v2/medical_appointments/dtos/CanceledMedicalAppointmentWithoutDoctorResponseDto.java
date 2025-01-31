package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

public class CanceledMedicalAppointmentWithoutDoctorResponseDto extends MedicalAppointmentWithoutDoctorResponseDto {

    private String canceledAt;

    public CanceledMedicalAppointmentWithoutDoctorResponseDto() {
    }

    private CanceledMedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = "%s%s[%s]".formatted(
                medicalAppointment.getCanceledAt(),
                medicalAppointment.getCanceledAtZoneOffset(),
                medicalAppointment.getCanceledAtZoneId()
        );
    }
    public static CanceledMedicalAppointmentWithoutDoctorResponseDto from(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentWithoutDoctorResponseDto(medicalAppointment);
    }

    public String getCanceledAt() {
        return canceledAt;
    }
}
