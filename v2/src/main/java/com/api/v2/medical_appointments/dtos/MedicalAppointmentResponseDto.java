package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalAppointmentResponseDto {

    private String id;
    private CustomerResponseDto customer;
    private DoctorResponseDto doctor;
    private String type;
    private LocalDateTime bookedAt;
    private ZoneId bookAtZoneId;

    public MedicalAppointmentResponseDto() {
    }

    protected MedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.getId().toString();
        this.customer = CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer());
        this.doctor = DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor());
        this.type = medicalAppointment.getType();
        this.bookedAt = medicalAppointment.getBookedAt();
        this.bookAtZoneId = medicalAppointment.getBookedAtZoneId();
    }

    public static MedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDto(medicalAppointment);
    }

    public String getId() {
        return id;
    }

    public CustomerResponseDto getCustomer() {
        return customer;
    }

    public DoctorResponseDto getDoctor() {
        return doctor;
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
