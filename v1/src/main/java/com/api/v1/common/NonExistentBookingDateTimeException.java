package com.api.v1.common;

public class NonExistentBookingDateTimeException extends RuntimeException {
    public NonExistentBookingDateTimeException() {
        super("Provided booking date and time is not associated with an active medical slot.");
    }
}
