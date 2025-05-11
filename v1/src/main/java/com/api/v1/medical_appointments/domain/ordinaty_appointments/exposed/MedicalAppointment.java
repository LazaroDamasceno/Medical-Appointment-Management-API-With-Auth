package com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed;

import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.responses.ordinary_appointments.CanceledMedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.responses.ordinary_appointments.CompletedMedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.responses.ordinary_appointments.MedicalAppointmentResponseDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class MedicalAppointment {

    private String id;
    private Customer customer;
    private Doctor doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;
    private LocalDateTime paidAt;

    private MedicalAppointment() {
    }

    private MedicalAppointment(Customer customer,
                               Doctor doctor,
                               LocalDateTime bookedAt
    ) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.doctor = doctor;
        this.status = MedicalAppointmentStatus.ACTIVE;
        this.bookedAt = bookedAt;
        this.createdAt = LocalDateTime.now();
    }

    public static MedicalAppointment of(Customer customer,
                                        Doctor doctor,
                                        LocalDateTime bookedAt
    ) {
        return new MedicalAppointment(
                customer,
                doctor,
                bookedAt
        );
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now();
        status = MedicalAppointmentStatus.CANCELED;
    }

    public void markAsCompleted() {
        completedAt = LocalDateTime.now();
        status = MedicalAppointmentStatus.COMPLETED;
    }

    public void markAsPaid() {
        paidAt = LocalDateTime.now();
        status = MedicalAppointmentStatus.PAID;
    }

    public MedicalAppointmentResponseDto toDto() {
        if (canceledAt != null && completedAt == null) {
            return CanceledMedicalAppointmentResponseDto.from(this);
        }
        else if (canceledAt == null && completedAt != null) {
            return CompletedMedicalAppointmentResponseDto.from(this);
        }
        return MedicalAppointmentResponseDto.from(this);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public MedicalAppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
