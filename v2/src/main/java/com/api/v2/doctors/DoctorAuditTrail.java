package com.api.v2.doctors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record DoctorAuditTrail(
        @Id
        String id,
        Doctor doctor,
        LocalDateTime createdAAt
) {

    public static DoctorAuditTrail of(Doctor doctor) {
        return new DoctorAuditTrail(
                UUID.randomUUID().toString(),
                doctor,
                LocalDateTime.now()
        );
    }

}
