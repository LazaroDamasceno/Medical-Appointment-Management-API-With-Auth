package com.api.v1.doctors.exceptions;

public class ActiveDoctorException extends RuntimeException {
    public ActiveDoctorException(String licenseNumber) {
        super("Doctor whose id is %s is active.".formatted(licenseNumber));
    }
}
