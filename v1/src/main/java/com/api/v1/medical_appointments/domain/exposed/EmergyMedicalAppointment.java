package com.api.v1.medical_appointments.domain.exposed;

import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.responses.CompletedEmergencyMedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.responses.EmergencyMedicalAppointmentResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class EmergyMedicalAppointment {

    @Id
    private String id;
    private Customer customer;
    private Doctor doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private BigDecimal price;

    private EmergyMedicalAppointment() {}

    private EmergyMedicalAppointment(Customer customer, Doctor doctor, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.doctor = doctor;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.status = MedicalAppointmentStatus.ACTIVE;
    }

    public static EmergyMedicalAppointment of(Customer customer, Doctor doctor, double price) {
        return new EmergyMedicalAppointment(customer, doctor, BigDecimal.valueOf(price));
    }

    void markAsCompleted() {
        status = MedicalAppointmentStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public EmergencyMedicalAppointmentResponseDto toDto() {
        if (completedAt != null) {
            return CompletedEmergencyMedicalAppointmentResponseDto.from(this);
        }
        return EmergencyMedicalAppointmentResponseDto.from(this);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public MedicalAppointmentStatus getStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
