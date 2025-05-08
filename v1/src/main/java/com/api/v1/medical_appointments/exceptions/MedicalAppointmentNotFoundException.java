package com.api.v1.medical_appointments.exceptions;

public class MedicalAppointmentNotFoundException extends RuntimeException {
    public MedicalAppointmentNotFoundException(String id) {
        super("Medical appointment whose id is %s was not found.".formatted(id));
    }
}
