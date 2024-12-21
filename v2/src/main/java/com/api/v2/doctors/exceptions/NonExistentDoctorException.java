package com.api.v2.doctors.exceptions;

public class NonExistentDoctorException extends RuntimeException {
    public NonExistentDoctorException(String medicalLicenseNumber) {
        super("The doctor whose license number is %s was not found.".formatted(medicalLicenseNumber));
    }
}