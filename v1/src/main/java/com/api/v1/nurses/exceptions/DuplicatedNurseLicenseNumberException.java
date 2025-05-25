package com.api.v1.nurses.exceptions;

public class DuplicatedNurseLicenseNumberException extends RuntimeException {
    public DuplicatedNurseLicenseNumberException() {
        super("Provided nurse license number is already in use.");
    }
}
