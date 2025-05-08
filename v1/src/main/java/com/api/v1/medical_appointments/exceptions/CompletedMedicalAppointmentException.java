package com.api.v1.medical_appointments.exceptions;

public class CompletedMedicalAppointmentException extends RuntimeException {
    public CompletedMedicalAppointmentException(String id) {
        super("Medical appointment whose id is %s is currently completed.".formatted(id));
    }
}
