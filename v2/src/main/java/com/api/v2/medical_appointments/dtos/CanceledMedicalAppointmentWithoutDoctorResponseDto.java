package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalAppointmentWithoutDoctorResponseDto extends MedicalAppointmentWithoutDoctorResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledMedicalAppointmentWithoutDoctorResponseDto() {
    }

    private CanceledMedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = medicalAppointment.getCanceledAt();
        this.canceledAtZone = medicalAppointment.getCanceledAtZone();
    }
    public static CanceledMedicalAppointmentWithoutDoctorResponseDto from(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentWithoutDoctorResponseDto(medicalAppointment);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
