package com.api.v1.common;

public class DuplicatedLicenseNumberException extends RuntimeException {
    public DuplicatedLicenseNumberException() {
        super("Provided license number is already in use.");
    }
}
