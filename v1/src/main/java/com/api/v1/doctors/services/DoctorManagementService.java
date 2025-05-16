package com.api.v1.doctors.services;

import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.http.ResponseEntity;

public interface DoctorManagementService {
    ResponseEntity<DoctorResponseDto> terminate(String licenseNumber);
    ResponseEntity<DoctorResponseDto> rehire(String licenseNumber);
}
