package com.api.v2.doctors.exceptions;

public class ImmutableDoctorException extends RuntimeException {
    public ImmutableDoctorException(String message) {
        super(message);
    }
}
