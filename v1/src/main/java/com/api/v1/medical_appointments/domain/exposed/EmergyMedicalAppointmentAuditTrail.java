package com.api.v1.medical_appointments.domain.exposed;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public record EmergyMedicalAppointmentAuditTrail(
        @Id
        String id,
        EmergyMedicalAppointment emergyMedicalAppointment,
        LocalDateTime createdAt
) {

    public static EmergyMedicalAppointmentAuditTrail of(EmergyMedicalAppointment emergyMedicalAppointment) {
        return new EmergyMedicalAppointmentAuditTrail(
                UUID.randomUUID().toString(),
                emergyMedicalAppointment,
                LocalDateTime.now()
        );
    }
}
