package com.api.v1.medical_appointments;

import java.time.LocalDateTime;

public final class CancelledMedicalAppointmentResponseDTO extends MedicalAppointmentResponseDTO {

    private final LocalDateTime cancelledAt;

    private CancelledMedicalAppointmentResponseDTO(
            MedicalAppointment medicalAppointment,
            LocalDateTime cancelledAt
    ) {
        super(medicalAppointment);
        this.cancelledAt = cancelledAt;
    }

    public static CancelledMedicalAppointmentResponseDTO from(MedicalAppointment medicalAppointment, LocalDateTime cancelledAt) {
        return new CancelledMedicalAppointmentResponseDTO(medicalAppointment, cancelledAt);
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
