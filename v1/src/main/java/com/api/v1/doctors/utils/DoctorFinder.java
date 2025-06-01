package com.api.v1.doctors.utils;

import com.api.v1.doctors.domain.Doctor;

public interface DoctorFinder {
    Doctor findByLicenseNumber(String licenseNumber);
    Doctor findActiveByLicenseNumber(String licenseNumber);
}
