package com.api.v1.doctors.exceptions;

public class DuplicatedMedicalLicenseNumberException extends RuntimeException {
    public DuplicatedMedicalLicenseNumberException() {
        super("Given medical license number is already in use.");
    }
}
