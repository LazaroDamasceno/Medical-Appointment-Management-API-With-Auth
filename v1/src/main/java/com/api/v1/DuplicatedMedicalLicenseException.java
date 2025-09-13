package com.api.v1;

public class DuplicatedMedicalLicenseException extends RuntimeException {
    public DuplicatedMedicalLicenseException(String id, String state) {
        super(ErrorMessage.duplicatedMedicalLicense(id, state));
    }
}
