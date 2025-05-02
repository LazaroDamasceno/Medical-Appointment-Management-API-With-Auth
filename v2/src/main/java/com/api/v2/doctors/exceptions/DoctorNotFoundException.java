package com.api.v2.doctors.exceptions;

import com.api.v2.doctors.domain.MedicalLicenseNumber;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(MedicalLicenseNumber mln) {
        super("Doctor whose medical license number is %s-%s was not found.".formatted(mln.licenseNumber(), mln.state()));
    }
}
