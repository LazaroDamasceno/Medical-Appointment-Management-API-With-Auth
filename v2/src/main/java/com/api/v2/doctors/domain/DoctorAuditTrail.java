package com.api.v2.doctors.domain;

import com.api.v2.common.DstCheckerUtil;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.*;

public record DoctorAuditTrail(
        @BsonId
        ObjectId id,
        Doctor doctor,
        LocalDate createdAt,
        ZoneId createdAtZoneId,
        ZoneOffset createdAtZoneOffset,
        boolean isCreatedDuringDST
) {

    public static DoctorAuditTrail create(Doctor doctor) {
        return new DoctorAuditTrail(
                new ObjectId(),
                doctor,
                LocalDate.now(),
                ZoneId.systemDefault(),
                OffsetDateTime.now().getOffset(),
                DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault())
        );
    }
}
