package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CanceledMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime canceledAt;
    private ZoneOffset canceledAtZone;

    public CanceledMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = medicalAppointment.getCanceledAt();
        this.canceledAtZone = medicalAppointment.getCanceledAtZone();
    }

    public static CanceledMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneOffset getCanceledAtZone() {
        return canceledAtZone;
    }
}
