package com.api.v1.doctors.exceptions;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String id) {
        super("Doctor whose id is %s was not found.".formatted(id));
    }
}
