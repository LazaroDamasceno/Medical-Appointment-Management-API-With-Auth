package com.api.v1.medical_appointments.exceptions;

public class ActiveMedicalAppointmentException extends RuntimeException {
    public ActiveMedicalAppointmentException(String id) {
        super("Medical appointment whose id is %s is currently active.".formatted(id));
    }
}
