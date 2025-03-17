package com.api.v2.common;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

public final class BlockedDateTimeHandler {

    public static Mono<Void> handle(LocalDate date) {
        if (DateTimeChecker.isBeforeToday(date)) {
            return Mono.error(new BlockedBookingDateTimeException());
        }
        return Mono.empty();
    }
}
