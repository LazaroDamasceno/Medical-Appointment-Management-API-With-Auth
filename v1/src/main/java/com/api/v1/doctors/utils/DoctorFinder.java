package com.api.v1.doctors.utils;

import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DoctorFinder {

    private DoctorFinder() {
    }

    private DoctorRepository doctorRepository;

    private DoctorFinder(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Optional<Doctor> findActiveByLicenseNumber(String licenseNumber) {
        return doctorRepository.findActiveByLicenseNumber(licenseNumber);
    }
}
