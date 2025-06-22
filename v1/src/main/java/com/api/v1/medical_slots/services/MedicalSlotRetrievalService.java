package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.DefaultMedicalSlotResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MedicalSlotRetrievalService {
    ResponseEntity<DefaultMedicalSlotResponseDTO> findByIdAndDoctor(String id, String medicalLicenseNumber);
    ResponseEntity<Page<DefaultMedicalSlotResponseDTO>> findAllByDoctor(String medicalLicenseNumber, Pageable pageable);
    ResponseEntity<Page<DefaultMedicalSlotResponseDTO>> findAll(Pageable pageable);
}
