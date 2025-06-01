package com.api.v1.doctors;

public interface DoctorFinder {
    Doctor findByLicenseNumber(String licenseNumber);
    Doctor findActiveByLicenseNumber(String licenseNumber);
}
