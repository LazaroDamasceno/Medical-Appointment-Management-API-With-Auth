package com.api.v1.doctors;

public class TerminatedDoctorException extends RuntimeException {
    public TerminatedDoctorException(String licenseNumber) {
        super("Doctor whose license number is %s is currently terminated.".formatted(licenseNumber));
    }
}
