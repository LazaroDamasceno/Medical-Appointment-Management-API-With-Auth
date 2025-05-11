package com.api.v1.people.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public record Person(
        @Id
        String id,
        String firstName,
        String middleName,
        String lastName,
        String sin,
        LocalDateTime birthDate,
        String email,
        LocalDateTime createdAt
) {
}
