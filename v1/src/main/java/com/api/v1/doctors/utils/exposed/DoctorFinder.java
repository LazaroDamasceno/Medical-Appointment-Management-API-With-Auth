package com.api.v1.doctors.utils.exposed;

import com.api.v1.doctors.domain.exposed.Doctor;

public interface DoctorFinder {
    Doctor findActiveByLicenseNumber(String licenseNumber);
}
