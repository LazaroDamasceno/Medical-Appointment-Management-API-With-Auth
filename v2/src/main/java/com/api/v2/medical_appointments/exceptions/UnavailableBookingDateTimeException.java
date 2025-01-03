package com.api.v2.medical_appointments.exceptions;

import java.time.LocalDateTime;

public class UnavailableBookingDateTimeException extends RuntimeException {
    public UnavailableBookingDateTimeException(LocalDateTime bookedAt) {
        super("The given booking datetime %s is already registered for another active medical appointment.".formatted(bookedAt));
    }
}
