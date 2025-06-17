package com.api.v1.medical_appointments;

import com.api.v1.customers.Customer;
import com.api.v1.doctors.Doctor;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "MedicalAppointments")
public record MedicalAppointment(
        @Id
        String id,
        Customer customer,
        Doctor doctor,
        MedicalAppointmentStatus status,
        LocalDateTime createdAt,
        LocalDateTime bookedAt,
        LocalDateTime cancelledAt,
        LocalDateTime completedAt
) {

    public static MedicalAppointment of(Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return new MedicalAppointment(
                UUID.randomUUID().toString(),
                customer,
                doctor,
                MedicalAppointmentStatus.ACTIVE,
                LocalDateTime.now(),
                bookedAt,
                null,
                null
        );
    }

    public MedicalAppointmentResponseDTO toDTO() {
        if (this.status.equals(MedicalAppointmentStatus.CANCELLED)) {
            return CancelledMedicalAppointmentResponseDTO.from(this);
        }
        else if (this.status.equals(MedicalAppointmentStatus.COMPLETED)) {
            return CompletedMedicalAppointmentResponseDTO.from(this);
        }
        return MedicalAppointmentResponseDTO.from(this);
    }

    public MedicalAppointment markAsCancelled() {
        return new MedicalAppointment(
                this.id,
                this.customer,
                this.doctor,
                MedicalAppointmentStatus.CANCELLED,
                this.createdAt,
                this.bookedAt,
                LocalDateTime.now(),
                null
        );
    }

    public MedicalAppointment markAsCompleted() {
        return new MedicalAppointment(
                this.id,
                this.customer,
                this.doctor,
                MedicalAppointmentStatus.COMPLETED,
                this.createdAt,
                this.bookedAt,
                null,
                LocalDateTime.now()
        );
    }
}
