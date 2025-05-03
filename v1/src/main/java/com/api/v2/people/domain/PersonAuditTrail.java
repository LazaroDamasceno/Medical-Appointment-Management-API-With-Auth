package com.api.v1.people.domain;

import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record PersonAuditTrail(
        @Id
        String id,
        Person person,
        LocalDateTime createdAt
) {

    public static PersonAuditTrail of(Person person) {
        return new PersonAuditTrail(
                UUID.randomUUID().toString(),
                person,
                LocalDateTime.now()
        );
    }
}
