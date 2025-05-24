package com.api.v1.nurses.services;

import com.api.v1.nurses.responses.NurseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NurseRetrievalService {
    ResponseEntity<NurseResponseDTO> findByLicenseNumber(String licenseNumber);
    ResponseEntity<Page<NurseResponseDTO>> findAll(Pageable pageable);
}
