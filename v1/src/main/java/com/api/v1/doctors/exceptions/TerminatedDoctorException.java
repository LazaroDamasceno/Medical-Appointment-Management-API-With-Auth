package com.api.v1.doctors.exceptions;

public class TerminatedDoctorException extends RuntimeException {
    public TerminatedDoctorException(String licenseNumber) {
        super("Doctor whose license number is %s is currently terminated.".formatted(licenseNumber));
    }
}
