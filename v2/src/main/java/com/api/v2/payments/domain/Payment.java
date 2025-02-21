package com.api.v2.payments.domain;

import com.api.v2.cards.domain.Card;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Document
public record Payment(
        @BsonId
        ObjectId id,
        Card card,
        MedicalAppointment medicalAppointment,
        LocalDateTime payedAt,
        ZoneId payedAtZoneId,
        ZoneOffset payedAtZoneOffset
) {

    public static Payment of(Card card, MedicalAppointment medicalAppointment) {
        return new Payment(
                new ObjectId(),
                card,
                medicalAppointment,
                LocalDateTime.now(),
                ZoneId.systemDefault(),
                OffsetTime.now().getOffset()
        );
    }
}
