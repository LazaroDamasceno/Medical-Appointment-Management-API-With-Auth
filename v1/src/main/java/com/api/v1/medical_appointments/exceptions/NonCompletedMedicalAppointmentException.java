package com.api.v1.medical_appointments.exceptions;

public class NonCompletedMedicalAppointmentException extends RuntimeException {
    public NonCompletedMedicalAppointmentException(String id) {
        super("Medical appointment whose id is %s is not completed.".formatted(id));
    }
}
