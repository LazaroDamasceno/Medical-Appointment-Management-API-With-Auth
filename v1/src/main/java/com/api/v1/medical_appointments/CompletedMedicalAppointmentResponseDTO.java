package com.api.v1.medical_appointments;

import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;

import java.time.LocalDateTime;

public final class CompletedMedicalAppointmentResponseDTO extends MedicalAppointmentResponseDTO {

    private final LocalDateTime completedAt;

    private CompletedMedicalAppointmentResponseDTO(
            String id,
            CustomerResponseDTO customer,
            DoctorResponseDTO doctor,
            MedicalAppointmentStatus status,
            LocalDateTime createdAt,
            LocalDateTime bookedAt,
            LocalDateTime completedAt
    ) {
        super(id, customer, doctor, status, createdAt, bookedAt);
        this.completedAt = completedAt;
    }
    public static CompletedMedicalAppointmentResponseDTO from(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentResponseDTO(
                medicalAppointment.id(),
                medicalAppointment.customer().toDto(),
                medicalAppointment.doctor().toDTO(),
                MedicalAppointmentStatus.COMPLETED,
                medicalAppointment.createdAt(),
                medicalAppointment.bookedAt(),
                medicalAppointment.completedAt()
        );
    }

    public LocalDateTime geCompletedAt() {
        return completedAt;
    }
}
