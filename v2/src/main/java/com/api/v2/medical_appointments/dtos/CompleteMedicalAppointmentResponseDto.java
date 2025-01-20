package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompleteMedicalAppointmentResponseDto {

    private String id;
    private CustomerResponseDto customerResponseDto;
    private DoctorResponseDto doctorResponseDto;
    private String type;
    private LocalDateTime bookedAt;
    private ZoneId bookAtZone;

    public CompleteMedicalAppointmentResponseDto() {
    }

    protected CompleteMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.getId().toString();
        this.customerResponseDto = CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer());
        this.doctorResponseDto = DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor());
        this.type = medicalAppointment.getType();
        this.bookedAt = medicalAppointment.getBookedAt();
        this.bookAtZone = medicalAppointment.getBookedAtZone();
    }

    public static CompleteMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CompleteMedicalAppointmentResponseDto(medicalAppointment);
    }

    public String getId() {
        return id;
    }

    public CustomerResponseDto getCustomerResponseDto() {
        return customerResponseDto;
    }

    public DoctorResponseDto getDoctorResponseDto() {
        return doctorResponseDto;
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
