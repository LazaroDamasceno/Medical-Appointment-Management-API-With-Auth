package com.api.v1.doctors.exceptions;


public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException() {
        super("Sought doctor whose license number was not found.");
    }
}
