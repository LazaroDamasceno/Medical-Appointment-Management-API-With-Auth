package com.api.v1.people.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public record PersonAuditTrail(
        @Id
        String id,
        Person person,
        LocalDateTime createdAt
) {
}
