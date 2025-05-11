package com.api.v1.nurses.exceptions;

public class ActiveNurseException extends RuntimeException {
    public ActiveNurseException(String licenseNumber) {
        super("Nurse whose license number is %s is active.".formatted(licenseNumber));
    }
}
