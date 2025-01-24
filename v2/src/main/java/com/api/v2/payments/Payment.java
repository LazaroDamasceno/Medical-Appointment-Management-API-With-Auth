package com.api.v2.payments;

import com.api.v2.cards.domain.Card;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public record Payment(
        @BsonId
        ObjectId id,
        MedicalAppointment medicalAppointment,
        Card card,
        LocalDateTime createdAt,
        ZoneId createdAtZoneId
) {

    public static Payment create(MedicalAppointment medicalAppointment, Card card) {
        return new Payment(
                new ObjectId(),
                medicalAppointment,
                card,
                LocalDateTime.now(),
                ZoneId.systemDefault()
        );
    }
}
