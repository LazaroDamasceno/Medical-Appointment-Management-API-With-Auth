package com.api.v1.nurses.domain;

import com.api.v1.nurses.domain.exposed.Nurse;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record NurseAuditTrail(
        @Id
        String id,
        Nurse nurse,
        LocalDateTime createdAt
) {

    public static NurseAuditTrail of(Nurse nurse) {
        return new NurseAuditTrail(
                UUID.randomUUID().toString(),
                nurse,
                LocalDateTime.now()
        );
    }
}
