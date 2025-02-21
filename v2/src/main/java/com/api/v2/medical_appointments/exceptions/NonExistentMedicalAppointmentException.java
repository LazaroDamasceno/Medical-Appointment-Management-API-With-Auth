package com.api.v2.medical_appointments.exceptions;

public class NonExistentMedicalAppointmentException extends RuntimeException {
    public NonExistentMedicalAppointmentException(@Id String id) {
        super("Medical appointment whose id is %s was not found.".formatted(id));
    }
}
