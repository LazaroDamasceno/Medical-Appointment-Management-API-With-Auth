package com.api.v1.payments.domain;

import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Payment(
        @Id
        String id,
        MedicalAppointment medicalAppointment,
        BigDecimal price,
        LocalDateTime createdAt
) {

    public static Payment of(MedicalAppointment medicalAppointment, double price) {
        return new Payment(
                UUID.randomUUID().toString(),
                medicalAppointment,
                BigDecimal.valueOf(price),
                LocalDateTime.now()
        );
    }
}
