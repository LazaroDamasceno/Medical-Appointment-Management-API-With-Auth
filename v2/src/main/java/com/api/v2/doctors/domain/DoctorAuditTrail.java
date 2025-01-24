package com.api.v2.doctors.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.ZoneId;

public record DoctorAuditTrail(
        @BsonId
        ObjectId id,
        Doctor doctor,
        LocalDate createdAt,
        ZoneId createdAtZoneId
) {

    public static DoctorAuditTrail create(Doctor doctor) {
        return new DoctorAuditTrail(
                new ObjectId(),
                doctor,
                LocalDate.now(),
                ZoneId.systemDefault()
        );
    }
}
