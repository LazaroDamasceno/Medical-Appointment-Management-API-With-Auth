package com.api.v1.nurses.utils.exposed;

import com.api.v1.nurses.domain.exposed.Nurse;

public interface NurseFinder {
    Nurse findByLicenseNumber(String licenseNumber);
}
