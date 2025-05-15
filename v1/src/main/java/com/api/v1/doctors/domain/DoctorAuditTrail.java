package com.api.v1.doctors.domain;

import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record DoctorAuditTrail(
        @Id
        String id,
        Person person,
        LocalDateTime createdAt
) {

    public static DoctorAuditTrail of(Person person) {
        return new DoctorAuditTrail(
                UUID.randomUUID().toString(),
                person,
                LocalDateTime.now()
        );
    }
}
