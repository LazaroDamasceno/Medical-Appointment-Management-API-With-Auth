package com.api.v2.people.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Document
public record PersonAuditTrail(
        @BsonId
        ObjectId id,
        Person person,
        LocalDateTime createdAt,
        ZoneOffset createdAtZone
) {

    public static PersonAuditTrail create(Person person) {
        return new PersonAuditTrail(
                new ObjectId(),
                person,
                OffsetDateTime.now().toLocalDateTime(),
                OffsetDateTime.now().getOffset()
        );
    }
}
