package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.DefaultMedicalSlotResponseDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface MedicalSlotRegistrationService {
    ResponseEntity<DefaultMedicalSlotResponseDTO> register(String medicalLicenseNumber, LocalDateTime availableAt);
}
