package com.api.v2.payments.domain;

import com.api.v2.cards.domain.Card;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Document
public record Payment(
        @Id
        String id,
        Card card,
        MedicalAppointment medicalAppointment,
        LocalDateTime paidAt,
        ZoneId paidAtZoneId,
        ZoneOffset paidAtZoneOffset
) {

    public static Payment of(Card card, MedicalAppointment medicalAppointment) {
        return new Payment(
                UUID.randomUUID().toString(),
                card,
                medicalAppointment,
                LocalDateTime.now(),
                ZoneId.systemDefault(),
                OffsetTime.now().getOffset()
        );
    }
}
