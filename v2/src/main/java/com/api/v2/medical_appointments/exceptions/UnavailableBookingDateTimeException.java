package com.api.v2.medical_appointments.exceptions;

public class UnavailableBookingDateTimeException extends RuntimeException {
    public UnavailableBookingDateTimeException() {
        super("The given booking datetime has not medical slot who has equal available datetime.");
    }
}
