package com.api.v2.cards.domain;

import com.api.v2.cards.CardRegistrationDto;
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

    public static Card create(CardRegistrationDto registrationDto) {
        return new Card(
                new ObjectId(),
                registrationDto.type(),
                registrationDto.bank(),
                registrationDto.cvv_cvc(),
                registrationDto.dueDate(),
                LocalDateTime.now(),
                ZoneId.systemDefault()
        );
    }
}
