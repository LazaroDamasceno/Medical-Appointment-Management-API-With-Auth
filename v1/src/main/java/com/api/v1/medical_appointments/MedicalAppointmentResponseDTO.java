package com.api.v1.medical_appointments;

import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public sealed class MedicalAppointmentResponseDTO
        extends RepresentationModel<MedicalAppointmentResponseDTO>
        permits CancelledMedicalAppointmentResponseDTO, CompletedMedicalAppointmentResponseDTO {

    private String id;
    private CustomerResponseDTO customer;
    private DoctorResponseDTO doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime bookedAt;

    protected MedicalAppointmentResponseDTO() {
    }

    protected MedicalAppointmentResponseDTO(
            String id,
            CustomerResponseDTO customer,
            DoctorResponseDTO doctor,
            MedicalAppointmentStatus status,
            LocalDateTime createdAt,
            LocalDateTime bookedAt
    ) {
        this.id = id;
        this.customer = customer;
        this.doctor = doctor;
        this.status = status;
        this.createdAt = createdAt;
        this.bookedAt = bookedAt;
    }

    public static MedicalAppointmentResponseDTO from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDTO(
                medicalAppointment.id(),
                medicalAppointment.customer().toDto(),
                medicalAppointment.doctor().toDTO(),
                MedicalAppointmentStatus.ACTIVE,
                medicalAppointment.createdAt(),
                medicalAppointment.bookedAt()
        );
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
