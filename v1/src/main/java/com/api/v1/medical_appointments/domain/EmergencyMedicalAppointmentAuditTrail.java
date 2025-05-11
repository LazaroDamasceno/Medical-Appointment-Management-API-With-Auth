package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.domain.exposed.EmergencyMedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record EmergencyMedicalAppointmentAuditTrail(
        @Id
        String id,
        EmergencyMedicalAppointment emergencyMedicalAppointment,
        LocalDateTime createdAt
) {

    public static EmergencyMedicalAppointmentAuditTrail of(EmergencyMedicalAppointment emergencyMedicalAppointment) {
        return new EmergencyMedicalAppointmentAuditTrail(
                UUID.randomUUID().toString(),
                emergencyMedicalAppointment,
                LocalDateTime.now()
        );
    }
}
