package com.api.v2.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DataTimeAdapterUtil {

    public static LocalDateTime set(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long offSet = zonedDateTime.getOffset().getTotalSeconds() / 3600L;
        return localDateTime.plusHours(offSet);
    }
}
