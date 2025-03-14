package com.api.v2.doctors.domain;

import com.api.v2.common.DstCheckerUtil;
import org.springframework.data.annotation.Id;

import java.time.*;
import java.util.UUID;

public record DoctorAuditTrail(
        @Id
        String id,
        Doctor doctor,
        LocalDate createdAt,
        ZoneId createdAtZoneId,
        ZoneOffset createdAtZoneOffset,
        boolean isCreatedDuringDST
) {

    public static DoctorAuditTrail of(Doctor doctor) {
        return new DoctorAuditTrail(
                UUID.randomUUID().toString(),
                doctor,
                LocalDate.now(),
                ZoneId.systemDefault(),
                OffsetDateTime.now().getOffset(),
                DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault())
        );
    }
}
