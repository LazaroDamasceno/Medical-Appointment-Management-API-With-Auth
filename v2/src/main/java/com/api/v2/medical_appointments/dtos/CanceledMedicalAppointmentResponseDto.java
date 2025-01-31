package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

public class CanceledMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private String canceledAt;

    public CanceledMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = "%s%s[%s]".formatted(
                medicalAppointment.getCanceledAt(),
                medicalAppointment.getCanceledAtZoneOffset(),
                medicalAppointment.getCanceledAtZoneId()
        );
    }

    public static CanceledMedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(medicalAppointment);
    }

    public String getCanceledAt() {
        return canceledAt;
    }

}
