package com.api.v2.medical_appointments.exceptions;

public class DuplicatedGivenBookingDateTimeException extends RuntimeException {
    public DuplicatedGivenBookingDateTimeException() {
        super("The given booking datetime is already in use.");
    }
}
