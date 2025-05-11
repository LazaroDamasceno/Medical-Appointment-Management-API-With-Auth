package com.api.v1.medical_appointments.responses;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.exposed.EmergencyMedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class EmergencyMedicalAppointmentResponseDto extends RepresentationModel<EmergencyMedicalAppointmentResponseDto> {

    private String id;
    private CustomerResponseDto customer;
    private DoctorResponseDto doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime createdAt;

    protected EmergencyMedicalAppointmentResponseDto() {
    }

    protected EmergencyMedicalAppointmentResponseDto(String id,
                                                     CustomerResponseDto customer,
                                                     DoctorResponseDto doctor,
                                                     MedicalAppointmentStatus status,
                                                     LocalDateTime createdAt
    ) {
        this.id = id;
        this.customer = customer;
        this.doctor = doctor;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static EmergencyMedicalAppointmentResponseDto from(EmergencyMedicalAppointment appointment) {
        return new EmergencyMedicalAppointmentResponseDto(
                appointment.getId(),
                appointment.getCustomer().toDto(),
                appointment.getDoctor().toDto(),
                appointment.getStatus(),
                appointment.getCreatedAt()
        );
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MedicalAppointmentStatus getStatus() {
        return status;
    }
}
