package com.api.v1.doctors.exceptions;

public class ActiveDoctorException extends RuntimeException {
    public ActiveDoctorException(String id) {
        super("Doctor whose id is %s is active.".formatted(id));
    }
}
