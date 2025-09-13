package com.api.v1;

public class DuplicatedMedicalLicenseException extends RuntimeException {
    public DuplicatedMedicalLicenseException() {
        super("Medical license is already registered");
    }
}
