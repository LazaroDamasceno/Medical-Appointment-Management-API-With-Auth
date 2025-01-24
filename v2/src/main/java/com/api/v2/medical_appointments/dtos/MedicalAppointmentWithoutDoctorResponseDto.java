package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalAppointmentWithoutDoctorResponseDto {

    private String id;
    private CustomerResponseDto customer;
    private String type;
    private LocalDateTime bookedAt;
    private ZoneId bookAtZoneId;

    public MedicalAppointmentWithoutDoctorResponseDto() {
    }

    protected MedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.getId().toString();
        this.customer = CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer());
        this.type = medicalAppointment.getType();
        this.bookedAt = medicalAppointment.getBookedAt();
        this.bookAtZoneId = medicalAppointment.getBookedAtZoneId();
    }

    public static MedicalAppointmentWithoutDoctorResponseDto from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentWithoutDoctorResponseDto(medicalAppointment);
    }

    public String getId() {
        return id;
    }

    public CustomerResponseDto getCustomer() {
        return customer;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public ZoneId getBookAtZoneId() {
        return bookAtZoneId;
    }
}
