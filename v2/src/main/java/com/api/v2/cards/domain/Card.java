package com.api.v2.cards.domain;

import com.api.v2.cards.dtos.CardRegistrationDto;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class Card {

        @BsonId
        private ObjectId id;
        private String type;
        private String bank;
        private String cvv_cvc;
        private LocalDate dueDate;
        private LocalDateTime createdAt;
        private ZoneId createdAtZone;
        private LocalDateTime bookedForDeletionAt;

    public Card() {
    }

    private Card(
            String type,
            String bank,
            String cvv_cvc,
            LocalDate dueDate
    ) {
        this.id = new ObjectId();
        this.type = type;
        this.bank = bank;
        this.cvv_cvc = cvv_cvc;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.createdAtZone = ZoneId.systemDefault();
    }

    public static Card create(String type, CardRegistrationDto registrationDto) {
        return new Card(
                type,
                registrationDto.bank(),
                registrationDto.cvv_cvc(),
                registrationDto.dueDate()
        );
    }
}
