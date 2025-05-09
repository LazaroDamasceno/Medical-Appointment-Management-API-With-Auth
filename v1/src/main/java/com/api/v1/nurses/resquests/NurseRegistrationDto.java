package com.api.v1.nurses.resquests;

import com.api.v1.nurses.dtos.NurseLicenseNumber;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;

public record NurseRegistrationDto(
        @Valid
        PersonRegistrationDto person,
        @Valid
        NurseLicenseNumber licenseNumber
) {
}
