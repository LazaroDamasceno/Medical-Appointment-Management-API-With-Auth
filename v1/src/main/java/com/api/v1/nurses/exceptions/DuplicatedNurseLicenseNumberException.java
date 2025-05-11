package com.api.v1.nurses.exceptions;

public class DuplicatedNurseLicenseNumberException extends RuntimeException {

    public DuplicatedNurseLicenseNumberException(String licenseNumber) {
        super("Nurse whose license number is %s currently in use.".formatted(licenseNumber));
    }
}
