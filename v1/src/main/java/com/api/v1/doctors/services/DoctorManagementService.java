package com.api.v1.doctors.services;

import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.http.ResponseEntity;

public interface DoctorManagementService {
    ResponseEntity<Void> terminate(String licenseNumber);
    ResponseEntity<Void> rehire(String licenseNumber);
}
