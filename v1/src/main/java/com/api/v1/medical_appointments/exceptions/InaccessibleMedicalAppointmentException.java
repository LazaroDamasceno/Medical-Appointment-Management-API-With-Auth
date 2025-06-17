package com.api.v1.medical_appointments.exceptions;

public class InaccessibleMedicalAppointmentException extends RuntimeException {
    public InaccessibleMedicalAppointmentException(String message) {
        super(message);
    }
}
