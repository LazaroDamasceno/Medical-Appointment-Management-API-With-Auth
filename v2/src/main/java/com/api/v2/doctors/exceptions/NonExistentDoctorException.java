package com.api.v2.doctors.exceptions;

import com.api.v2.common.MLN;

public class NonExistentDoctorException extends RuntimeException {
    public NonExistentDoctorException(@MLN String medicalLicenseNumber) {
        super("The doctor whose license number is %s was not found.".formatted(medicalLicenseNumber));
    }
}