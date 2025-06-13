package com.api.v1.doctors.services;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.people.PersonRegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorRegistrationService {
    ResponseEntity<DoctorResponseDTO> register(String medicalLicenseNumber, PersonRegistrationDTO registrationDTO);
}
