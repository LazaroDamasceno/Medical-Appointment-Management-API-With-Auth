package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.MedicalSlotResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MedicalSlotRetrievalService {
    ResponseEntity<MedicalSlotResponseDTO> findByIdAndDoctor(String id, String medicalLicenseNumber);
    ResponseEntity<Page<MedicalSlotResponseDTO>> findAllByDoctor(String medicalLicenseNumber, Pageable pageable);
    ResponseEntity<Page<MedicalSlotResponseDTO>> findAll(Pageable pageable);
}
