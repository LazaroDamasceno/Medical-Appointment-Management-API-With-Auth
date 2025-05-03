package com.api.v1.doctors.exceptions;

public class TerminatedDoctorException extends RuntimeException {
    public TerminatedDoctorException(String id) {
        super("Doctor whose id is %s is terminated.".formatted(id));
    }
}
