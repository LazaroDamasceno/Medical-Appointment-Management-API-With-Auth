package com.api.v1.medical_appointments;

import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;

import java.time.LocalDateTime;

public final class CancelledMedicalAppointmentResponseDTO extends MedicalAppointmentResponseDTO {

    private final LocalDateTime cancelledAt;

    private CancelledMedicalAppointmentResponseDTO(
            String id,
            CustomerResponseDTO customer,
            DoctorResponseDTO doctor,
            MedicalAppointmentStatus status,
            LocalDateTime createdAt,
            LocalDateTime bookedAt,
            LocalDateTime cancelledAt
    ) {
        super(id, customer, doctor, status, createdAt, bookedAt);
        this.cancelledAt = cancelledAt;
    }

    public static CancelledMedicalAppointmentResponseDTO from(MedicalAppointment medicalAppointment) {
        return new CancelledMedicalAppointmentResponseDTO(
                medicalAppointment.id(),
                medicalAppointment.customer().toDto(),
                medicalAppointment.doctor().toDTO(),
                MedicalAppointmentStatus.CANCELLED,
                medicalAppointment.createdAt(),
                medicalAppointment.bookedAt(),
                medicalAppointment.cancelledAt()
        );
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
