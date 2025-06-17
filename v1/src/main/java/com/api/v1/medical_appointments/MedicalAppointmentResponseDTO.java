package com.api.v1.medical_appointments;

import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class MedicalAppointmentResponseDTO
        extends RepresentationModel<MedicalAppointmentResponseDTO> {

    private String id;
    private CustomerResponseDTO customer;
    private DoctorResponseDTO doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime bookedAt;

    protected MedicalAppointmentResponseDTO() {
    }

    protected MedicalAppointmentResponseDTO(MedicalAppointment medicalAppointment) {
        this.id = medicalAppointment.id();
        this.customer = medicalAppointment.customer().toDto();
        this.doctor = medicalAppointment.doctor().toDTO();
        this.status = medicalAppointment.status();
        this.createdAt = medicalAppointment.createdAt();
        this.bookedAt = medicalAppointment.bookedAt();
    }

    public MedicalAppointmentResponseDTO from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDTO(medicalAppointment);
    }

    public String getId() {
        return id;
    }

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public DoctorResponseDTO getDoctor() {
        return doctor;
    }

    public MedicalAppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
}
