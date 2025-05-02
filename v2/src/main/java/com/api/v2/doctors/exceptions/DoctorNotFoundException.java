package com.api.v2.doctors.exceptions;

import com.api.v2.doctors.domain.MedicalLicenseNumber;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String id) {
        super("Doctor whose id is %s was not found.".formatted(id));
    }
}
