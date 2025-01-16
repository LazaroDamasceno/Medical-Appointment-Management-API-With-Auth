package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MedicalAppointmentResponseDto {

    private ObjectId id;
    private CustomerResponseDto customerResponseDto;
    private DoctorResponseDto doctorResponseDto;
    private String type;
    private LocalDateTime bookedAt;
    private ZoneId bookAtZone;

    public MedicalAppointmentResponseDto() {
    }

    protected MedicalAppointmentResponseDto(
            ObjectId id,
            CustomerResponseDto customerResponseDto,
            DoctorResponseDto doctorResponseDto,
            String type,
            LocalDateTime bookedAt,
            ZoneId bookAtZone
    ) {
        this.id = id;
        this.customerResponseDto = customerResponseDto;
        this.doctorResponseDto = doctorResponseDto;
        this.type = type;
        this.bookedAt = bookedAt;
        this.bookAtZone = bookAtZone;
    }

    public static MedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                medicalAppointment.getType(),
                medicalAppointment.getBookedAt(),
                medicalAppointment.getBookedAtZone()
        );
    }

    public ObjectId getId() {
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
