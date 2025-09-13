package com.api.v1.physicians.exceptions;

public class DuplicatedMedicalLicenseException extends RuntimeException {
    public DuplicatedMedicalLicenseException() {
        super("Medical license is already registered");
    }
}
