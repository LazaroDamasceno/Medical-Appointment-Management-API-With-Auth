package com.api.v1.nurses.exceptions;

public class TerminatedNurseException extends RuntimeException {
    public TerminatedNurseException(String licenseNumber) {
        super("Nurse whose license number is %s is terminated.".formatted(licenseNumber));
    }
}
