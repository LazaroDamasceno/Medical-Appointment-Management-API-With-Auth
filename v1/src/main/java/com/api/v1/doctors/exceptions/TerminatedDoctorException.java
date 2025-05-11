package com.api.v1.doctors.exceptions;

public class TerminatedDoctorException extends RuntimeException {
    public TerminatedDoctorException(String licenseNumber) {
        super("Doctor whose id is %s is terminated.".formatted(licenseNumber));
    }
}
