package com.api.v1.medical_appointments.exceptions;

public class SelfBookingAppointmentException extends RuntimeException {
    public SelfBookingAppointmentException(String doctorLicenseNumber) {
        super("""
                Doctor whose license number is %s cannot booked a medical appointment whose doctor's medical slot is themself.
          """.formatted(doctorLicenseNumber)
        );
    }
}
