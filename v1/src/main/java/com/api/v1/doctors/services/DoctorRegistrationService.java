package com.api.v1.doctors.services;

import com.api.v1.doctors.requests.DoctorRegistrationDTO;
import com.api.v1.doctors.DoctorResponseDto;
import org.springframework.http.ResponseEntity;

public interface DoctorRegistrationService {
    ResponseEntity<DoctorResponseDto> register(DoctorRegistrationDTO registrationDTO);
}
