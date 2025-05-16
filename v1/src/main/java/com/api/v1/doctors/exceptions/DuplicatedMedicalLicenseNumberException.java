package com.api.v1.doctors.exceptions;

public class DuplicatedMedicalLicenseNumberException extends RuntimeException {
    public DuplicatedMedicalLicenseNumberException(String licenseNumber) {
        super("Provided license number is currently in use.");
    }
}
