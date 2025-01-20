package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledCompleteMedicalAppointmentResponseDto extends CompleteMedicalAppointmentResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledCompleteMedicalAppointmentResponseDto() {
    }

    private CanceledCompleteMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = medicalAppointment.getCanceledAt();
        this.canceledAtZone = medicalAppointment.getCanceledAtZone();
    }

    public static CanceledCompleteMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CanceledCompleteMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
