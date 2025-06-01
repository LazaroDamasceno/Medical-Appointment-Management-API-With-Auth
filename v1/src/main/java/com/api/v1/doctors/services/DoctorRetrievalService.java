package com.api.v1.doctors.services;

import com.api.v1.doctors.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorRetrievalService {
    ResponseEntity<DoctorResponseDto> findByLicenseNumber(String licenseNumber);
    ResponseEntity<Page<DoctorResponseDto>> findAll(Pageable pageable);
}
