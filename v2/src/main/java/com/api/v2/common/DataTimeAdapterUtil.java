package com.api.v2.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DataTimeAdapterUtil {

    public static LocalDateTime adapt(LocalDateTime localDateTime) {
        return localDateTime.plusHours(offSetZone(localDateTime));
    }

    public static long offSetZone(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zonedDateTime.getOffset().getTotalSeconds() / 3600L;
    }
}
