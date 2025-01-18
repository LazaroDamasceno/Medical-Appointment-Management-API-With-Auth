package com.api.v2.cards.domain;

import com.api.v2.cards.dtos.CardRegistrationDto;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public record Card(
        @BsonId
        ObjectId id,
        String type,
        String bank,
        String cvv_cvc,
        LocalDate dueDate,
        LocalDateTime createdAt,
        ZoneId createdAtZone
) {

    public static Card create(
            String type,
            String bank,
            String cvv_cvc,
            LocalDate dueDate
    ) {
        return new Card(
                new ObjectId(),
                type,
                bank,
                cvv_cvc,
                dueDate,
                LocalDateTime.now(),
                ZoneId.systemDefault()
        );
    }
}
