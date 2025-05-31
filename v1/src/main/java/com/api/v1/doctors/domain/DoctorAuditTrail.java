package com.api.v1.doctors.domain;

import com.api.v1.doctors.Doctor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "DoctorAuditTrail")
public record DoctorAuditTrail(
        @Id
        String id,
        Doctor doctor,
        LocalDateTime createdAt
) {

    public static DoctorAuditTrail of(Doctor doctor) {
        return new DoctorAuditTrail(
                UUID.randomUUID().toString(),
                doctor,
                LocalDateTime.now()
        );
    }
}
