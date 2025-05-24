package com.api.v1.doctors.utils;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.exceptions.DoctorNotFoundException;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import org.springframework.stereotype.Component;

@Component
public final class DoctorFinderImpl implements DoctorFinder {

    private final DoctorCrudRepository doctorCrudRepository;

    public DoctorFinderImpl(DoctorCrudRepository doctorCrudRepository) {
        this.doctorCrudRepository = doctorCrudRepository;
    }

    @Override
    public Doctor findByLicenseNumber(@LicenseNumber String licenseNumber) {
        return doctorCrudRepository
                .findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new DoctorNotFoundException(licenseNumber));
    }

    public Doctor findActiveByLicenseNumber(@LicenseNumber String licenseNumber) {
        return doctorCrudRepository
                .findActiveByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new DoctorNotFoundException(licenseNumber));
    }

    public Doctor findTerminatedByLicenseNumber(@LicenseNumber String licenseNumber) {
        return doctorCrudRepository
                .findTerminatedByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new DoctorNotFoundException(licenseNumber));
    }
}
