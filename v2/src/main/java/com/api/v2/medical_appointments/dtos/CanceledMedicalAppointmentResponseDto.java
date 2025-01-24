package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;

    public CanceledMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = medicalAppointment.getCanceledAt();
        this.canceledAtZoneId = medicalAppointment.getCanceledAtZoneId();
    }

    public static CanceledMedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZoneId() {
        return canceledAtZoneId;
    }
}
