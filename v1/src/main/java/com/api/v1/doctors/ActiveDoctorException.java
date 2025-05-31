package com.api.v1.doctors;

public class ActiveDoctorException extends RuntimeException {
    public ActiveDoctorException(String licenseNumber) {
        super("Doctor whose license number is %s is currently active.".formatted(licenseNumber));
    }
}
