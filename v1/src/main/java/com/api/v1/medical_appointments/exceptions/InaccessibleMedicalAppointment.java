package com.api.v1.medical_appointments.exceptions;

public class InaccessibleMedicalAppointment extends RuntimeException {
    public InaccessibleMedicalAppointment() {
        super("Provided customer is not the customer of the sought medical appointment.");
    }
}
