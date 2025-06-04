package com.api.v1.doctors.services;

import com.api.v1.doctors.DoctorRegistrationDTO;
import com.api.v1.doctors.DoctorResponseDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorRegistrationService {
    ResponseEntity<DoctorResponseDTO> register(DoctorRegistrationDTO registrationDTO);
}
