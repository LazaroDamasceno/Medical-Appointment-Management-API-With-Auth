package com.api.v1.medical_appointments.exceptions;

public class PaidMedicalAppointmentException extends RuntimeException {
    public PaidMedicalAppointmentException(String id) {
        super("Medical appointment whose id is %s is currently paid.".formatted(id));
    }
}
