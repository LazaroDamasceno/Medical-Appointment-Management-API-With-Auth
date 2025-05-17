package com.api.v1.doctors.utils.exposed;

import com.api.v1.doctors.domain.exposed.Doctor;

public interface DoctorFinder {
    Doctor findByLicenseNumber(String licenseNumber);
    Doctor findActiveByLicenseNumber(String licenseNumber);
}
