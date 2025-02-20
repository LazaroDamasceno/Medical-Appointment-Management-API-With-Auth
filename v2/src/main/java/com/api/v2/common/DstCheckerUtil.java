package com.api.v2.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DstCheckerUtil {
    public static boolean isGivenDateTimeFollowingDST(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        return zonedDateTime
                .getZone()
                .getRules()
                .isDaylightSavings(zonedDateTime.toInstant());
    }
}
