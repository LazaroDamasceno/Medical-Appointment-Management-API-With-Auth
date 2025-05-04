package com.api.v1.common;

public class DuplicatedBookingDateTimeException extends RuntimeException {
    public DuplicatedBookingDateTimeException(String message) {
        super(message);
    }
}
