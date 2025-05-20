package com.api.v1.doctors.utils;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.exceptions.DoctorNotFoundException;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DoctorFinderImpl implements DoctorFinder {

    private final DoctorCrudRepository doctorCrudRepository;

    public DoctorFinderImpl(DoctorCrudRepository doctorCrudRepository) {
        this.doctorCrudRepository = doctorCrudRepository;
    }

    @Override
    public Doctor findByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorCrudRepository.findByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }

    public Doctor findActiveByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorCrudRepository.findActiveByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }

    public Doctor findTerminatedByLicenseNumber(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optional = doctorCrudRepository.findTerminatedByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            throw new DoctorNotFoundException(licenseNumber);
        }
        return optional.get();
    }
}
