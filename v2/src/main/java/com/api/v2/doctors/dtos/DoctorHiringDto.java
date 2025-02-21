package com.api.v2.doctors.dtos;

import com.api.v2.common.MLN;
import com.api.v2.people.dtos.PersonRegistrationDto;

public record DoctorHiringDto(
        @MLN String medicalLicenseNumber,
        PersonRegistrationDto person
) {
}
