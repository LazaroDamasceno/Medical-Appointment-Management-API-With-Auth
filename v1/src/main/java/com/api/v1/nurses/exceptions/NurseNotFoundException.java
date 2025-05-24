package com.api.v1.nurses.exceptions;

public class NurseNotFoundException extends RuntimeException {
    public NurseNotFoundException(String licenseNumber) {
        super("Nurse whose license number is %s was not found.".formatted(licenseNumber));
    }
}
