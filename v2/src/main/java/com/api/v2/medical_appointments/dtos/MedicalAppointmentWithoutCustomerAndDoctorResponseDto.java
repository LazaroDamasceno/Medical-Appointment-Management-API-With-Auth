package com.api.v2.medical_appointments.dtos;

import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalAppointmentWithoutCustomerAndDoctorResponseDto {

    private String id;
    private String type;
    private LocalDateTime bookedAt;
    private ZoneId bookAtZone;

    public MedicalAppointmentWithoutCustomerAndDoctorResponseDto() {
    }

    protected MedicalAppointmentWithoutCustomerAndDoctorResponseDto(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.getId().toString();
        this.type = medicalAppointment.getType();
        this.bookedAt = medicalAppointment.getBookedAt();
        this.bookAtZone = medicalAppointment.getBookedAtZone();
    }

    public static MedicalAppointmentWithoutCustomerAndDoctorResponseDto create(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentWithoutCustomerAndDoctorResponseDto(medicalAppointment);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public ZoneId getBookAtZone() {
        return bookAtZone;
    }
}
