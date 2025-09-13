package com.api.v1;

public class DuplicatedMedicalLicenseNumberException extends RuntimeException {
    public DuplicatedMedicalLicenseNumberException(String licenseNumber, String state) {
        super(ErrorMessage.duplicatedMedicalLicense(licenseNumber, state));
    }
}
