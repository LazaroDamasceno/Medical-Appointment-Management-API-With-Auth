package com.api.v1.medical_appointments;

import java.time.LocalDateTime;

public final class CompletedMedicalAppointmentResponseDTO extends MedicalAppointmentResponseDTO {

    private final LocalDateTime completedAt;

    private CompletedMedicalAppointmentResponseDTO(
            MedicalAppointment medicalAppointment,
            LocalDateTime completedAt
    ) {
        super(medicalAppointment);
        this.completedAt = completedAt;
    }

    public static CompletedMedicalAppointmentResponseDTO from(
            MedicalAppointment medicalAppointment,
            LocalDateTime completedAt
    ) {
        return new CompletedMedicalAppointmentResponseDTO(medicalAppointment, completedAt);
    }

    public LocalDateTime geCompletedAt() {
        return completedAt;
    }
}
