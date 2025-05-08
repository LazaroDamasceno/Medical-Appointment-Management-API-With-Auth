package com.api.v1.medical_appointments.domain.exposed;

import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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

    private MedicalAppointment(Customer customer,
                               Doctor doctor,
                               MedicalAppointmentStatus status,
                               LocalDateTime bookedAt
    ) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.doctor = doctor;
        this.status = status;
        this.bookedAt = bookedAt;
        this.createdAt = LocalDateTime.now();
    }

    public static MedicalAppointment of(Customer customer,
                                        Doctor doctor,
                                        MedicalAppointmentStatus status,
                                        LocalDateTime bookedAt
    ) {
        return new MedicalAppointment(
                customer,
                doctor,
                status,
                bookedAt
        );
    }

    public MedicalAppointmentResponseDto toDto() {
        return MedicalAppointmentResponseDto.from(this);
    }
}
