package com.api.v1.nurses.utils;

import com.api.v1.common.LicenseNumber;
import com.api.v1.nurses.domain.NurseCrudRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.exceptions.NurseNotFoundException;
import com.api.v1.nurses.utils.exposed.NurseFinder;
import org.springframework.stereotype.Component;

@Component
public final class NurseFinderImpl implements NurseFinder {

    private final NurseCrudRepository crudRepository;

    public NurseFinderImpl(NurseCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Nurse findByLicenseNumber(@LicenseNumber String licenseNumber) {
        return crudRepository
                .findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new NurseNotFoundException(licenseNumber));
    }
}
