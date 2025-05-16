package com.api.v1.doctors.utils;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DoctorFinderImpl implements DoctorFinder {

    private final DoctorRepository doctorRepository;

    public DoctorFinderImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Optional<Doctor> findActiveByLicenseNumber(@LicenseNumber String licenseNumber) {
        return doctorRepository.findActiveByLicenseNumber(licenseNumber);
    }
}
