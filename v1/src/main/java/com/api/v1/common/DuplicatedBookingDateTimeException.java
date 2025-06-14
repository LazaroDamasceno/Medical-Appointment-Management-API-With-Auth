package com.api.v1.common;

public class DuplicatedBookingDateTimeException extends RuntimeException {
    public DuplicatedBookingDateTimeException() {
        super("Given booking date and time is currently in use.");
    }
}
