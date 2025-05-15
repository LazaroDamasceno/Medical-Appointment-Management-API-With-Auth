package com.api.v1.doctors.services;

import com.api.v1.common.Result;
import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorRetrievalService {
    ResponseEntity<Result<DoctorResponseDto>> findByLicenseNumber(String licenseNumber);
    ResponseEntity<Result<Page<DoctorResponseDto>>> findAll(Pageable pageable);
}
