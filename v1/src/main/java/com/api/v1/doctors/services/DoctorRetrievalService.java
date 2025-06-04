package com.api.v1.doctors.services;

import com.api.v1.doctors.DoctorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorRetrievalService {
    ResponseEntity<DoctorResponseDTO> findByLicenseNumber(String licenseNumber);
    ResponseEntity<Page<DoctorResponseDTO>> findAll(Pageable pageable);
}
