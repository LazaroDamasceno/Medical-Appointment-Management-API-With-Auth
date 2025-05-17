package com.api.v1.doctors.utils;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.exceptions.DoctorNotFoundException;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DoctorFinderImpl implements DoctorFinder {

    private final DoctorRepository doctorRepository;

    public DoctorFinderImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorRepository.findByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }

    public Doctor findActiveByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorRepository.findActiveByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }

    public Doctor findTerminatedByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorRepository.findTerminatedByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }
}
