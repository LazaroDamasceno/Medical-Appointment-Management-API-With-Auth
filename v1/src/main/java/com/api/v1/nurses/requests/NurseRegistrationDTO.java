package com.api.v1.nurses.requests;

import com.api.v1.common.LicenseNumber;
import com.api.v1.people.requests.PersonRegistrationDTO;
import jakarta.validation.Valid;

public record NurseRegistrationDTO(
        @LicenseNumber
        String licenseNumber,
        @Valid
        PersonRegistrationDTO person
) {
}
