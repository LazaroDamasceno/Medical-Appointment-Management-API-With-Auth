package com.api.v1.doctors.exceptions;


public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String licenseNumber) {
        super("Doctor whose license number is %s was not found.".formatted(licenseNumber));
    }
}
