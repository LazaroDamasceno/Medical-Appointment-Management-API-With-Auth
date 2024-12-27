package com.api.v2.medical_appointments.exceptions;

public class UnavailableGivenBookingDateTimeException extends RuntimeException {
    public UnavailableGivenBookingDateTimeException() {
        super("The given booking datetime has not medical slot who has equal available datetime.");
    }
}
