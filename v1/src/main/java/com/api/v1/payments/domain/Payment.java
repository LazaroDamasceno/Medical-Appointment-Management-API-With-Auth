package com.api.v1.payments.domain;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Payment(
        @Id
        String id,
        MedicalAppointment medicalAppointment,
        LocalDateTime createdAt
) {

    public static Payment of(MedicalAppointment medicalAppointment) {
        return new Payment(
                UUID.randomUUID().toString(),
                medicalAppointment,
                LocalDateTime.now()
        );
    }
}
