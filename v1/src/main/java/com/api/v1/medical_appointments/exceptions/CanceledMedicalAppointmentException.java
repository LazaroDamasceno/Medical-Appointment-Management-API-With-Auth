package com.api.v1.medical_appointments.exceptions;

public class CanceledMedicalAppointmentException extends RuntimeException {
    public CanceledMedicalAppointmentException(String id) {
        super("Medical appointment whose id is %s is currently canceled.".formatted(id));
    }
}
