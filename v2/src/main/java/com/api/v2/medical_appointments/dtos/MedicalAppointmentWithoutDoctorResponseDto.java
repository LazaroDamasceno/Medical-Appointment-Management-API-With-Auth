package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;

public class MedicalAppointmentWithoutDoctorResponseDto {

    private String id;
    private CustomerResponseDto customer;
    private String type;
    private String bookedAt;

    public MedicalAppointmentWithoutDoctorResponseDto() {
    }

    protected MedicalAppointmentWithoutDoctorResponseDto(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.getId().toString();
        this.customer = CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer());
        this.type = medicalAppointment.getType();
        this.bookedAt = "%s%s[%s]".formatted(
                medicalAppointment.getBookedAt(),
                medicalAppointment.getBookedAtZoneOffset(),
                medicalAppointment.getCanceledAtZoneId()
        );
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

    public String getBookedAt() {
        return bookedAt;
    }
}
