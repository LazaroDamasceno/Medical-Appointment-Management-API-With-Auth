package com.api.v2.doctors.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public record DoctorAuditTrail(
        @BsonId
        ObjectId id,
        Doctor doctor,
        LocalDate createdAt,
        ZoneId createdAtZoneId,
        ZoneOffset createdAtZoneOffset
) {

    public static DoctorAuditTrail create(Doctor doctor) {
        return new DoctorAuditTrail(
                new ObjectId(),
                doctor,
                LocalDate.now(),
                ZoneId.systemDefault(),
                OffsetDateTime.now().getOffset()
        );
    }
}
