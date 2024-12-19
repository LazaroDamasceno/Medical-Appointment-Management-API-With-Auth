package com.api.v2.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;

@Document
public record PersonAuditTrail(
        @BsonId
        ObjectId id,
        Person person,
        LocalDate createdAt,
        ZoneId createdAtZone
) {

    public static PersonAuditTrail create(Person person) {
        return new PersonAuditTrail(
                new ObjectId(),
                person,
                LocalDate.now(),
                ZoneId.systemDefault()
        );
    }
}
