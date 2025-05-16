package com.api.v1.doctors.utils;

import com.api.v1.doctors.domain.exposed.Doctor;

import java.util.Optional;

public interface DoctorFinder {
    Optional<Doctor> findActiveByLicenseNumber(String licenseNumber);
}
