package com.api.v1.doctors.services;

import com.api.v1.common.Result;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.http.ResponseEntity;

public interface DoctorRegistrationService {
    ResponseEntity<Result<DoctorResponseDto>> register(DoctorRegistrationDto registrationDto);
}
