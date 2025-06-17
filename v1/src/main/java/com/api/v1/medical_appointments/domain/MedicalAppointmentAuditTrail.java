package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.MedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "MedicalAppointmentAuditTrail")
public record MedicalAppointmentAuditTrail(
        @Id
        String id,
        MedicalAppointment medicalAppointment,
        LocalDateTime createdAt
) {

    public static MedicalAppointmentAuditTrail of(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentAuditTrail(
                UUID.randomUUID().toString(),
                medicalAppointment,
                LocalDateTime.now()
        );
    }
}
